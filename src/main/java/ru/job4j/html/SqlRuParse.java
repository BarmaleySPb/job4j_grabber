package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.Post;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final SqlRuDateTimeParser dateTimeParser;

    public SqlRuParse(SqlRuDateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> listOfLinks = new LinkedList<>();
        int numberPage = 1;
        while (numberPage < 6) {
            Document doc = Jsoup.connect(link + numberPage).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                listOfLinks.add(detail(href.attr("href")));
            }
            numberPage++;
        }
        return listOfLinks;
    }

    @Override
    public Post detail(String link) throws IOException {
        Post post = new Post();
            post.setLink(link);
        Document docPost = Jsoup.connect(link).get();
        Elements docPostTitle = docPost.select(".messageHeader");
        post.setTitle(docPostTitle.get(0).parent().child(0).text());
        Elements docPostDescription = docPost.select(".msgBody");
        post.setDescription(docPostDescription.get(0).parent().child(1).text());
        Elements docPostCreated = docPost.select(".msgFooter");
        String[] postCreated = docPostCreated.get(0).parent().child(0).text().split("\\[");
        post.setCreated(dateTimeParser.parse(postCreated[0]));
        return post;
    }
}