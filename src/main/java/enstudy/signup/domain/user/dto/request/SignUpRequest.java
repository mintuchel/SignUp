package enstudy.signup.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Size(message = "이메일은 30자를 넘을 수 없습니다.", max = 30)
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(message = "비밀번호는 8자 이상 12자 이하여야 합니다.", min = 8, max = 12)
        String password,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Size(message = "닉네임은 3자 이상 10자 이하여야 합니다.", min = 3, max = 10)
        String username,

        @NotBlank(message = "주소는 필수 입력 값입니다")
        @Size(message = "주소는 50자를 넘을 수 없습니다.", max = 50)
        String address
) { }