package pl.migibud.blog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.migibud.blog.comment.CommentError;
import pl.migibud.blog.comment.CommentException;
import pl.migibud.blog.post.PostError;
import pl.migibud.blog.post.PostException;
import pl.migibud.blog.user.UserError;
import pl.migibud.blog.user.UserException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(PostException.class)
    ResponseEntity<ErrorInfo> handlePostException(PostException e){
        HttpStatus httpStatus=null;
        if (PostError.POST_NOT_FOUND.equals(e.getPostError())){
            httpStatus=HttpStatus.NOT_FOUND;
        }
        if (PostError.POST_TITLE_ALREADY_EXISTS.equals(e.getPostError())){
            httpStatus=HttpStatus.BAD_REQUEST;
        }
        ErrorInfo errorInfo = new ErrorInfo(
                LocalDateTime.now(),
                httpStatus.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(errorInfo);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CommentException.class)
    ResponseEntity<ErrorInfo> handleCommentException(CommentException e){
        HttpStatus httpStatus=null;
        if (CommentError.COMMENT_NOT_FOUND.equals(e.getCommentError())){
            httpStatus=HttpStatus.NOT_FOUND;
        }
        if (CommentError.COMMENT_DOES_NOT_BELONG_TO_POST.equals(e.getCommentError())){
            httpStatus=HttpStatus.BAD_REQUEST;
        }
        ErrorInfo errorInfo = new ErrorInfo(
                LocalDateTime.now(),
                httpStatus.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(errorInfo);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserException.class)
    ResponseEntity<ErrorInfo> handleUserException(UserException e){
        HttpStatus httpStatus=null;
        if (UserError.USER_WITH_USERNAME_ALREADY_EXISTS.equals(e.getUserError())){
            httpStatus=HttpStatus.CONFLICT;
        }
        if (UserError.USER_WITH_EMAIL_ALREADY_EXISTS.equals(e.getUserError())){
            httpStatus=HttpStatus.CONFLICT;
        }
        ErrorInfo errorInfo = new ErrorInfo(
                LocalDateTime.now(),
                httpStatus.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(errorInfo);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ErrorInfo> handleAccessDeniedException(AccessDeniedException e){
        HttpStatus httpStatus=HttpStatus.UNAUTHORIZED;
        ErrorInfo errorInfo = new ErrorInfo(
                LocalDateTime.now(),
                httpStatus.toString(),
                e.getMessage()
        );
        return ResponseEntity.status(httpStatus).body(errorInfo);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(
                objectError -> {
                    String fieldName = ((FieldError) objectError).getField();
                    String defaultMessage = objectError.getDefaultMessage();
                    errors.put(fieldName,defaultMessage);
                }
        );
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
