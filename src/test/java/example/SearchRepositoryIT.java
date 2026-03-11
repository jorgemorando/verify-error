package example;

import example.domain.Search;
import example.persistence.SearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Triggers VerifyError when Spring Data JDBC persists Search with SearchPost collection.
 * Error: java.lang.VerifyError: Bad type on operand stack in putfield
 * Location: SearchPost__Accessor_*.setProperty(...)
 * Cause: ClassGeneratingPropertyAccessorFactory generates invalid bytecode for record without @Id
 */
@SpringBootTest
class SearchRepositoryIT {

    @Autowired
    private SearchRepository searchRepository;

    @Test
    void saveSearchWithPosts_triggersVerifyError() {
        UUID userId = UUID.randomUUID();
        Search search = new Search(userId);
        search.post(7);

        Search saved = searchRepository.save(search);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getPosts()).hasSize(1);
    }
}
