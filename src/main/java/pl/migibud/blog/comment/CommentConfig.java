package pl.migibud.blog.comment;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.migibud.blog.post.PostRepository;

@Configuration
class CommentConfig {

    @Bean
    CommentFacade commentFacade(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper){
        return new CommentService(commentRepository,postRepository,modelMapper);
    }
}
