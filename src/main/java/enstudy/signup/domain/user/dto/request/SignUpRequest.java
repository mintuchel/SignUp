package enstudy.signup.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        // 내부적인 정규식으로 통해 email 형식인지 판단
        @Email(message = "유효한 이메일 형식을 입력하세요.")
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Size(message = "이메일은 30자를 넘을 수 없습니다.", max = 30)
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(message = "비밀번호는 8자 이상 12자 이하여야 합니다.", min = 8, max = 12)
        String password,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Size(message = "닉네임은 2자 이상 10자 이하여야 합니다.", min = 2, max = 10)
        String username,

        @NotBlank(message = "도로명주소는 필수 입력 값입니다")
        @Size(message = "도로명주소는 30자를 넘을 수 없습니다.", max = 50)
        String streetAddress,

        // 상세주소는 빈값이여도 된다고 한다.. 대원피셜
        @Size(message = "상세주소는 30자를 넘을 수 없습니다.", max = 50)
        String detailAddress
) { }