package example.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table("searches")
public class Search {

    @Id
    @Column("id")
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("created_at")
    private Instant createdAt;

    @MappedCollection(idColumn = "search_id")
    private Set<SearchPost> posts = new HashSet<>();

    @PersistenceCreator
    public Search(UUID id, UUID userId, Instant createdAt, Set<SearchPost> posts) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.posts = posts != null ? posts : new HashSet<>();
    }

    public Search(UUID userId) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.createdAt = Instant.now();
    }

    public void post(int days) {
        if (days < 1 || days > 14) {
            throw new IllegalArgumentException("Days must be between 1 and 14");
        }
        posts.add(SearchPost.withExpirationDate(LocalDate.now().plusDays(days)));
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<SearchPost> getPosts() {
        return posts;
    }
}
