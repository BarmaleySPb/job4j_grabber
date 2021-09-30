package ru.job4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Post {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime created;

    public Post() {

    }

    public Post(String name, String text, String link, int id, LocalDateTime created) {
        this.title = name;
        this.description = text;
        this.link = link;
        this.id = id;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return title.equals(post.title)
                && link.equals(post.link)
                && description.equals(post.description)
                && created.equals(post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, link, description, created);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", link='" + link + '\''
                + ", description='" + description + '\''
                + ", created=" + FORMATTER.format(created)
                + '}';
    }
}
