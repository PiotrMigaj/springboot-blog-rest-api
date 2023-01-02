package pl.migibud.blog.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.migibud.blog.post.dto.PostDto;
import pl.migibud.blog.post.dto.PostResponse;
import pl.migibud.blog.util.ResponseUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

import static pl.migibud.blog.Constants.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
class PostController {

    private final PostFacade postFacade;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto createdPost = postFacade.createPost(postDto);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + createdPost.getId())).body(createdPost);
    }

    @GetMapping
    ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) int pageNo,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(required = false, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(required = false, defaultValue = DEFAULT_SORT_DIRECTION) String sortDir
    ) {
        return ResponseEntity.ok(postFacade.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postFacade.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<PostDto> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postFacade.updatePost(id, postDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Map<String, String>> deletePost(@PathVariable Long id) {
        postFacade.deletePost(id);
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, String> deleteResponse = ResponseUtil.entityDeleteResponse(Post.class, id, status);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(deleteResponse);
    }
}
