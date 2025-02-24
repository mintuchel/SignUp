package enstudy.signup.global.exception.exception;

import enstudy.signup.global.exception.errorcode.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException {
    private UserErrorCode userErrorCode;
}
