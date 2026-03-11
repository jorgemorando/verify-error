package example;

import example.domain.Search;
import example.persistence.SearchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(SearchRepositoryIT.class);


    @Autowired
    private SearchRepository searchRepository;

   

    @Test
    void updateSearchWithPosts_triggersVerifyError() {
        //THIS IS MY USE CASE: I CREATE A SEARCH WITHOUT POSTS, THEN ADD POSTS AND SAVE AGAIN. IT TRIGGERS THE ERROR ON THE SECOND SAVE.
        UUID userId = UUID.randomUUID();
        log.info("Creating search for user {}", userId);
        Search search = new Search(userId);
        Search saved = searchRepository.save(search);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getPosts()).hasSize(0);

        log.info("Saved initial search {}", saved.getId());
        log.info("Added post, saving again (triggers VerifyError)");

        saved.post(7);

        Search saved2 = searchRepository.save(saved);
        
        assertThat(saved2.getPosts()).hasSize(1);
        log.info("Save completed successfully");

    }

    @Test
    void saveSearchWithPosts_triggersVerifyError() {
        UUID userId = UUID.randomUUID();
        log.info("Creating search for user {}", userId);
        Search search = new Search(userId);
        

        //IT'S ALSO TRIGGERED WHEN SAVING COMPLETE AGGREGATE
        search.post(7);

        Search saved = searchRepository.save(search);

        log.info("Saved initial search {}", saved.getId());
        log.info("Added post, saving again (triggers VerifyError)");
        
        assertThat(saved.getPosts()).hasSize(1);
        log.info("Save completed successfully");

    }
}
