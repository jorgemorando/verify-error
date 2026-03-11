package example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("posts")
public class Post {

    @Id
    @Column("id")
    private Long id;

    @Column("title")
    private String title;

    @MappedCollection(idColumn = "post_id")
    private Set<Comment> comments = new HashSet<>();

    @PersistenceCreator
    public Post(Long id, String title, Set<Comment> comments) {
        this.id = id;
        this.title = title;
        this.comments = comments != null ? comments : new HashSet<>();
    }

    public Post(String title) {
        this.title = title;
    }

    public void addComment(String content) {
        comments.add(new Comment(content));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<Comment> getComments() {
        return comments;
    }
}
