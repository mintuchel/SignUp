package enstudy.signup.domain.emailverification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "이메일 인증 확인 요청")
public record EmailVerificationRequest(
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Schema(description = "이메일 주소", example = "ensharp25@naver.com")
        String email,
        @NotBlank(message = "인증코드는 필수 입력 값입니다.")
        @Schema(description = "인증코드", example = "123456")
        String verificationCode
) { }
