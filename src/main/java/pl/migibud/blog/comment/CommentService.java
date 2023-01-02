package pl.migibud.blog.comment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.migibud.blog.comment.dto.CommentDto;
import pl.migibud.blog.post.Post;
import pl.migibud.blog.post.PostError;
import pl.migibud.blog.post.PostException;
import pl.migibud.blog.post.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CommentService implements CommentFacade {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", postId)));

        Comment comment = new Comment(
                commentDto.getName(),
                commentDto.getEmail(),
                commentDto.getBody(),
                post
        );

        Comment save = commentRepository.save(comment);

        CommentDto savedCommentDto = new CommentDto(
                save.getId(),
                save.getName(),
                save.getEmail(),
                save.getBody()
        );

        return savedCommentDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByPostId(Long postId) {

        if (!postRepository.existsById(postId)) {
            throw new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", postId));
        }

        return commentRepository.findByPostId(postId).stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getName(),
                        comment.getEmail(),
                        comment.getBody()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", postId)));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentError.COMMENT_NOT_FOUND, String.format("Comment with id: %s not found", commentId)));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new CommentException(CommentError.COMMENT_DOES_NOT_BELONG_TO_POST, String.format("Comment with id: %s does not belong to post with id: %s", commentId, postId));
        }

        return new CommentDto(
                comment.getId(),
                comment.getName(),
                comment.getEmail(),
                comment.getBody()
        );
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public CommentDto updateCommentById(Long postId, Long commentId,CommentDto commentDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", postId)));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentError.COMMENT_NOT_FOUND, String.format("Comment with id: %s not found", commentId)));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new CommentException(CommentError.COMMENT_DOES_NOT_BELONG_TO_POST, String.format("Comment with id: %s does not belong to post with id: %s", commentId, postId));
        }

        comment.setName(commentDto.getName()!=null? commentDto.getName() : comment.getName());
        comment.setEmail(commentDto.getEmail()!=null? commentDto.getEmail() : comment.getEmail());
        comment.setBody(commentDto.getBody()!=null? commentDto.getBody() : comment.getBody());

        return new CommentDto(
                comment.getId(),
                comment.getName(),
                comment.getEmail(),
                comment.getBody()
        );
    }

    @Override
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostError.POST_NOT_FOUND, String.format("Post with id: %s not found", postId)));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentError.COMMENT_NOT_FOUND, String.format("Comment with id: %s not found", commentId)));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new CommentException(CommentError.COMMENT_DOES_NOT_BELONG_TO_POST, String.format("Comment with id: %s does not belong to post with id: %s", commentId, postId));
        }
        commentRepository.delete(comment);
    }
}
