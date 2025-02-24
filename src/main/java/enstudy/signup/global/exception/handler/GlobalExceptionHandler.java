package enstudy.signup.global.exception.handler;

import enstudy.signup.global.exception.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = UserException.class)
    protected ResponseEntity<String> handleUserException(UserException e) {
        return ResponseEntity
                .status(e.getUserErrorCode().getHttpStatus())
                .body(e.getMessage());
    }
}
