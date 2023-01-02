package pl.migibud.blog.post;

import pl.migibud.blog.post.dto.PostDto;
import pl.migibud.blog.post.dto.PostResponse;

import java.util.List;

public interface PostFacade {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(Long id,PostDto postDto);
    void deletePost(Long id);
}
