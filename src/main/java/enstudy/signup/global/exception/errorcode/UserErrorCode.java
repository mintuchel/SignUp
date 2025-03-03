package enstudy.signup.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    INVALID_EMAIL(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 옳지 않습니다"),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다"),
    PASSWORD_UPDATE_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "비밀번호 변경에 실패했습니다(MySQL 에러)");

    private final HttpStatus httpStatus;
    private final String message;
}
