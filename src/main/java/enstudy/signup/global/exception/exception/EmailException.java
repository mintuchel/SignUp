package enstudy.signup.global.exception.exception;

import enstudy.signup.global.exception.errorcode.EmailErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailException extends RuntimeException {
    private EmailErrorCode emailErrorCode;
}
