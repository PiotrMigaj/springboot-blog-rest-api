package pl.migibud.blog.comment;

import pl.migibud.blog.comment.dto.CommentDto;

import java.util.List;

public interface CommentFacade {

    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId,Long commentId);
    CommentDto updateCommentById(Long postId,Long commentId,CommentDto commentDto);
    void deleteComment(Long postId,Long commentId);

}
