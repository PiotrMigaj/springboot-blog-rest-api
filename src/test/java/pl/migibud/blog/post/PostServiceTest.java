package pl.migibud.blog.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.migibud.blog.post.dto.PostDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock(lenient = true)
    private PostRepository postRepository;
    private PostService postService;
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @BeforeEach
    void setUp(){
        modelMapper=new ModelMapper();
        postService = new PostService(postRepository,modelMapper);
    }



    @Test
    void givenPostDto_whenSavingIntoDb_thenShouldSavePostInDb(){
        //given
        PostDto postDto = new PostDto(
                null,
                "post 1",
                "post 1 desc",
                "post 1 content",
                null
        );
        //when
        PostDto post = postService.createPost(postDto);
        //then
        Mockito.verify(postRepository).save(modelMapper.map(postDto,Post.class));
    }

    @Test
    void givenPostDto_whenSavingIntoDbPostWithTitleThatExists_thenShouldThrowException(){
        //given
        PostDto postDto = new PostDto(
                null,
                "post 1",
                "post 1 desc",
                "post 1 content",
                null
        );
        Mockito.when(postRepository.existsByTitle(postDto.getTitle())).thenReturn(true);
        //when
        //then
        assertThatThrownBy(()->postService.createPost(postDto))
                .isNotNull()
                .isInstanceOf(PostException.class)
                .hasMessage("Post with such an title already exists.");
    }

    @Test
    void givenPostDto_whenSavingIntoDbPostWithTitleThatExists_thenShouldThrowException_anotherPossibility(){
        //given
        PostDto postDto = new PostDto(
                null,
                "post 1",
                "post 1 desc",
                "post 1 content",
                null
        );
        Mockito.when(postRepository.existsByTitle(postDto.getTitle())).thenReturn(true);
        //when
        //then
        try {
            postService.createPost(postDto);
            fail("PostException was not thrown.");
        }catch (PostException e){
            assertAll(
                    ()->assertThat(e).isNotNull(),
                    ()->assertThat(e).hasMessage("Post with such an title already exists.")
            );
        }
    }

    @Test
    void givenPostDto_whenSavingIntoDb_thenShouldSavePostInDb_lenientExample(){
        //given
        PostDto postDto = new PostDto(
                null,
                "post 1",
                "post 1 desc",
                "post 1 content",
                null
        );
        Mockito.when(postRepository.existsByTitle(postDto.getTitle())).thenReturn(true);
        Mockito.when(postRepository.save(any())).thenReturn(new Post(postDto.getTitle(),postDto.getDescription(),postDto.getContent()));
        //when
        //then
        assertThatThrownBy(()->postService.createPost(postDto))
                .isNotNull()
                .isInstanceOf(PostException.class)
                .hasMessage("Post with such an title already exists.");
    }

    @Test
    void givenPostDto_whenSavingIntoDb_thenShouldSavePostInDb_inOrder(){
        //given
        PostDto postDto = new PostDto(
                null,
                "post 1",
                "post 1 desc",
                "post 1 content",
                null
        );
        //when
        PostDto post = postService.createPost(postDto);
        //then
        final InOrder inOrder = Mockito.inOrder(postRepository);
        inOrder.verify(postRepository).existsByTitle(postDto.getTitle());
        inOrder.verify(postRepository).save(modelMapper.map(postDto,Post.class));
    }

    @Test
    void givenPostDto_whenSavingIntoDb_thenShouldSavePostInDb_argumentCaptor(){
        //given
        PostDto postDto = new PostDto(
                null,
                "post 1",
                "post 1 desc",
                "post 1 content",
                null
        );
        //when
        PostDto post = postService.createPost(postDto);
        //then
        Mockito.verify(postRepository).save(postArgumentCaptor.capture());
        assertAll(
                ()->assertThat(postArgumentCaptor.getValue()).isNotNull(),
                ()->assertThat(postArgumentCaptor.getValue()).isInstanceOf(Post.class),
                ()->assertThat(postArgumentCaptor.getValue()).isEqualTo(modelMapper.map(postDto,Post.class))
        );
    }





}