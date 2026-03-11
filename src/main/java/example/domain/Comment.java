package example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("comments")
public class Comment {

    @Id
    @Column("id")
    private Long id;

    @Column("post_id")
    private Long postId;

    @Column("content")
    private String content;

    @PersistenceCreator
    public Comment(Long id, Long postId, String content) {
        this.id = id;
        this.postId = postId;
        this.content = content;
    }

    public Comment(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public String getContent() {
        return content;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
