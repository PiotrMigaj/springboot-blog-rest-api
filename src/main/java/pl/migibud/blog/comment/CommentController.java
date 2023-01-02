package pl.migibud.blog.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.migibud.blog.comment.dto.CommentDto;
import pl.migibud.blog.util.ResponseUtil;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping("/posts/{postId}/comments")
    ResponseEntity<CommentDto> createComment(@PathVariable Long postId,@Valid @RequestBody CommentDto commentDto){
        CommentDto save = commentFacade.createComment(postId, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @GetMapping("/posts/{postId}/comments")
    ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentFacade.getCommentsByPostId(postId));
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId,@PathVariable Long commentId){
        return ResponseEntity.ok(commentFacade.getCommentById(postId,commentId));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<CommentDto> updateCommentById(@PathVariable Long postId,@PathVariable Long commentId,@Valid @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentFacade.updateCommentById(postId, commentId, commentDto));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    ResponseEntity<Map<String, String>> deleteCommentById(@PathVariable Long postId,@PathVariable Long commentId){
        commentFacade.deleteComment(postId,commentId);
        HttpStatus status = HttpStatus.ACCEPTED;
        Map<String, String> deleteResponse = ResponseUtil.entityDeleteResponse(Comment.class,commentId,status);
        return ResponseEntity.status(status).body(deleteResponse);
    }
}
