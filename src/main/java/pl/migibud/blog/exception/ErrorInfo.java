package pl.migibud.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
class ErrorInfo {

    private LocalDateTime localDateTime;
    private String httpStatus;
    private String message;
}
