package pl.migibud.blog.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentException extends RuntimeException{
    private final CommentError commentError;
    private final String message;
}
