package ru.job4j;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.grabber.PsqlStore;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;
import ru.job4j.html.SqlRuParse;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;


public class AlertRabbit implements AutoCloseable {

    private static Connection connection;


    private static Properties readConfig() throws Exception {
        Properties config = new Properties();
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            config.load(in);
            return config;
        }
    }

    private static void init(Properties config) {
        try {
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Properties config = readConfig();
        init(config);
        SqlRuDateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(dateTimeParser);
        List<Post> listOfPost = sqlRuParse.list("https://www.sql.ru/forum/job-offers/");
        System.out.println(listOfPost.size());
        PsqlStore psqlStore = new PsqlStore(config);
        for (Post post : listOfPost) {
            psqlStore.save(post);
        }
        System.out.println("All ready!!!");
        System.out.println(psqlStore.getAll());
        System.out.println(psqlStore.findById(45));


        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(config.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            try (Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
                 PreparedStatement statement =
                         connection.prepareStatement("insert into rabbit (created_date) values (?);")) {
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                statement.setTimestamp(1, timestamp);
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}