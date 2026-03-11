package example.domain;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Represents a posting period. No @Id - identified by composite key (search_id, created_at).
 * Trigger: Spring Data JDBC ClassGeneratingPropertyAccessorFactory generates invalid bytecode
 * for this record when persisting, causing VerifyError.
 */
@Table("search_posts")
public record SearchPost(

    @Column("search_id")
    UUID searchId,

    @Column("created_at")
    Instant createdAt,

    @Column("expires_on")
    String expirationDate
) {

    public SearchPost {
        // if (searchId == null) {
        //     throw new IllegalArgumentException("searchId is required");
        // }
        // if (createdAt == null) {
        //     throw new IllegalArgumentException("createdAt is required");
        // }
        // if (expirationDate == null) {
        //     throw new IllegalArgumentException("expirationDate is required");
        // }
    }

    public static SearchPost withExpirationDate(LocalDate expirationDate) {
        return new SearchPost(null, Instant.now(), expirationDate.toString());
    }
}
