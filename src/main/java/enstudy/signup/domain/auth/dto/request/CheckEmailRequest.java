package enstudy.signup.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "이메일 사용 가능 여부 확인 요청")
public record CheckEmailRequest(
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Schema(description = "이메일 주소", example = "ensharp25@naver.com")
        String email
) { }