package pl.migibud.blog.post;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PostConfig {

    @Bean
    PostFacade postFacade(PostRepository postRepository, ModelMapper modelMapper){
        return new PostService(postRepository,modelMapper);
    }
}
