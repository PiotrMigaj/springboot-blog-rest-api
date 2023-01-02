package pl.migibud.blog.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException{
    private final UserError userError;
    private final String message;
}
