package pl.migibud.blog.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @AfterEach
    void print(){
        testEntityManager.getEntityManager().createQuery("SELECT p FROM Post p",Post.class)
                .getResultList()
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @ArgumentsSource(FindByIdPostProvider.class)
    void givenListOfPosts_whenFindPostById_thenShouldCheckIfPostReturnedFromDb(
            List<Post> posts,
            Long id,
            boolean expected
    ){
        //given
        posts.forEach(testEntityManager::persist);
        //when
        Optional<Post> result = postRepository.findById(id);
        //then
        assertThat(result.isPresent()).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsToCheckIfPostsExists")
    void givenListOfPosts_whenCheckIfPostExistsById_thenShouldCheckIfPostExists(
            List<Post> posts,
            Long id,
            boolean expected
    ){
        //given
        posts.forEach(testEntityManager::persist);
        //when
        boolean result = postRepository.existsById(id);
        //then
        assertThat(result).isEqualTo(expected);
    }

    static Stream<Arguments> provideArgumentsToCheckIfPostsExists(){
        return Stream.of(
                Arguments.of(
                        Collections.emptyList(),
                        1L,
                        false
                ),
                Arguments.of(
                        List.of(
                                new Post("p1","p1 desc","p1 content"),
                                new Post("p2","p2 desc","p2 content")
                        ),
                        1L,
                        true
                ),
                Arguments.of(
                        List.of(
                                new Post("p1","p1 desc","p1 content"),
                                new Post("p2","p2 desc","p2 content")
                        ),
                        10L,
                        false
                )
        );
    }
}