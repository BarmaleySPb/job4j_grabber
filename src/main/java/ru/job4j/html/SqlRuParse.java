package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;

public class SqlRuParse {

    private static Post detail(String link) throws IOException {
        Post post = new Post();
        Document docPost = Jsoup.connect(link).get();
        Elements docPostDescription = docPost.select(".msgBody");
        post.setDescription(docPostDescription.get(0).parent().child(1).text());
        Elements docPostCreated = docPost.select(".msgFooter");
        String[] postCrated = docPostCreated.get(0).parent().child(0).text().split("\\[");
        SqlRuDateTimeParser dateTimeParser = new SqlRuDateTimeParser();
        post.setCreated(dateTimeParser.parse(postCrated[0]));
        return post;
    }

    public static void main(String[] args) throws Exception {
        int numberPage = 1;
        while (numberPage < 6) {
            Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + numberPage).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element parent = td.parent();
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(parent.child(5).text());
                detail(href.attr("href"));
            }
            numberPage++;
        }
    }
}