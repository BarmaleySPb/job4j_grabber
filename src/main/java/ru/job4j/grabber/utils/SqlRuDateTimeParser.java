package ru.job4j.grabber.utils;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "JANUARY"),
            Map.entry("фев", "FEBRUARY"),
            Map.entry("мар", "MARCH"),
            Map.entry("апр", "APRIL"),
            Map.entry("май", "MAY"),
            Map.entry("июн", "JUNE"),
            Map.entry("июл", "JULY"),
            Map.entry("авг", "AUGUST"),
            Map.entry("сен", "SEPTEMBER"),
            Map.entry("окт", "OCTOBER"),
            Map.entry("ноя", "NOVEMBER"),
            Map.entry("дек", "DECEMBER")
    );

    @Override
    public LocalDateTime parse(String parse) {
        LocalDateTime time;
        String[] str = parse.replace(",", "").replace(":", " ").split(" ");
        if (str[0].equals("вчера")) {
            time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
                    .minusDays(1)
                    .withHour(Integer.parseInt(str[1]))
                    .withMinute(Integer.parseInt(str[2]));
            return time;
        }
        if (str[0].equals("сегодня")) {
            time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
                    .withHour(Integer.parseInt(str[1]))
                    .withMinute(Integer.parseInt(str[2]));
            return time;
        }
        time = LocalDateTime.of(
                Integer.parseInt("20" + str[2]),
                Month.valueOf(MONTHS.get(str[1])),
                Integer.parseInt(str[0]),
                Integer.parseInt(str[3]),
                Integer.parseInt(str[4])
        );
        return time;
    }
}