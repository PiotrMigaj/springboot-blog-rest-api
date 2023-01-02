package pl.migibud.blog.post;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import pl.migibud.blog.comment.dto.CommentDto;
import pl.migibud.blog.post.dto.PostDto;
import pl.migibud.blog.post.dto.PostResponse;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class PostService implements PostFacade {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto) {
        if (postRepository.existsByTitle(postDto.getTitle())){
            throw new PostException(PostError.POST_TITLE_ALREADY_EXISTS, "Post with such an title already exists.");
        }
        Post post = modelMapper.map(postDto,Post.class);
        Post save = postRepository.save(post);
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getContent(),
                Set.of()
        );
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        PostResponse postResponse = new PostResponse(
                posts.getContent().stream()
                        .map(post -> new PostDto(
                                post.getId(),
                                post.getTitle(),
                                post.getDescription(),
                                post.getContent(),
                                post.getComments().stream()
                                        .map(comment -> modelMapper.map(comment, CommentDto.class))
                                        .collect(Collectors.toSet())
                        ))
                        .collect(Collectors.toList()),
                posts.getNumber(),
                posts.getSize(),
                posts.getTotalElements(),
                posts.getTotalPages(),
                posts.isLast()

        );
        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {

        return postRepository.findById(id)
                .map(post -> modelMapper.map(post,PostDto.class)
                )
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", id)));
    }

    @Override
    @Transactional
    public PostDto updatePost(Long id, PostDto postDto) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(postDto.getTitle() != null ? postDto.getTitle() : post.getTitle());
                    post.setDescription(postDto.getDescription() != null ? postDto.getDescription() : post.getDescription());
                    post.setContent(postDto.getContent() != null ? postDto.getContent() : post.getContent());
                    return modelMapper.map(post,PostDto.class);
                })
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", id)));
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", id)));
        postRepository.delete(post);
    }
}
