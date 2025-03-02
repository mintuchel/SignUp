package enstudy.signup.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmailErrorCode {

    INVALID_EMAIL(HttpStatus.NOT_FOUND, "해당 메일로 인증 코드가 전송된 기록이 없습니다"),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "인증 번호가 일치하지 않습니다"),
    MESSAGING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "메시지 전송 중 에러 발생");

    private final HttpStatus httpStatus;
    private final String message;
}
