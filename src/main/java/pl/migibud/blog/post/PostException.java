package pl.migibud.blog.post;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostException extends RuntimeException{
    private final PostError postError;
    private final String message;
}
