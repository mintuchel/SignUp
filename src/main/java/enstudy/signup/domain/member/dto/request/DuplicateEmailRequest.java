package enstudy.signup.domain.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// @Email 로 이메일 형식 검증
// @NotBlank 로 빈 값 방지
public record DuplicateEmailRequest(
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        String email
) { }
