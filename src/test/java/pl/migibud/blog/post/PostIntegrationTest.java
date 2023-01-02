package pl.migibud.blog.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.migibud.blog.SpringbootBlogRestApiApplication;
import pl.migibud.blog.post.dto.PostDto;
import pl.migibud.blog.util.JsonParserUtil;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringbootBlogRestApiApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
@AutoConfigureMockMvc
//@Import(SpringSecurityTestConfiguration.class)
class PostIntegrationTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @Test
    @WithMockUser(roles="ADMIN")
    void givenPostDto_whenRegisterPost_thenShouldReturnSavedPost() throws Exception {
        //given
        PostDto postDto = new PostDto("Post to save", "Post to save desc", "Post to save content");
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonParserUtil.asJsonString(postDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
