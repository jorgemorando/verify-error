package example;

import example.domain.Comment;
import example.domain.Post;
import example.persistence.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostRepositoryIT {

    @Autowired
    private PostRepository postRepository;

    @Test
    void savePostWithComments() {
        Post post = new Post("First Post");
        post.addComment("Great article!");
        post.addComment("Thanks for sharing.");

        Post saved = postRepository.save(post);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("First Post");
        assertThat(saved.getComments()).hasSize(2);
        assertThat(saved.getComments()).extracting(Comment::getContent).containsExactlyInAnyOrder("Great article!", "Thanks for sharing.");
    }

    @Test
    void savePostThenAddCommentAndSaveAgain() {
        Post post = new Post("Second Post");
        Post saved = postRepository.save(post);

        assertThat(saved.getComments()).isEmpty();

        saved.addComment("First comment");
        Post updated = postRepository.save(saved);

        assertThat(updated.getComments()).hasSize(1);
        assertThat(updated.getComments().iterator().next().getContent()).isEqualTo("First comment");
    }
}
