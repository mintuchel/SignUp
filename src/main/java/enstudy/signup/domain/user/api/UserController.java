package enstudy.signup.domain.user.api;

import enstudy.signup.domain.user.dto.request.CheckEmailRequest;
import enstudy.signup.domain.user.dto.request.LoginRequest;
import enstudy.signup.domain.user.dto.request.SignUpRequest;
import enstudy.signup.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController가 @ResponseBody를 포함하므로 리턴되는 객체가 자동으로 JSON으로 변환되며,
// 응답의 Content-Type이 기본적으로 application/json으로 설정됨

// Controller 단에서 ResponseEntity 생성하는게 좋음
// 1. SOLID 원칙 준수: 단일 책임 원칙(SRP)에 따라, 서비스는 비즈니스 로직만, 컨트롤러는 HTTP 요청/응답 처리를 담당하는 게 깔끔.
// 2. 테스트 용이성: 서비스 단이 HTTP와 독립적이어서 단위 테스트가 쉬움.

// DTO에 @NotBlank를 적용했다면, 컨트롤러에서 @Valid 또는 @Validated를 사용해 검증을 트리거해야함

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "회원가입/로그인 API", description = "박대원 김시원 신혜연 화이팅")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<Integer> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        int id = userService.signUp(signUpRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(id);
    }

    @PostMapping("/checkEmail")
    @Operation(summary = "이메일 중복 확인")
    public ResponseEntity<Boolean> checkIfEmailAvailable(@Valid @RequestBody CheckEmailRequest checkEmailRequest) {
        boolean isAvailable = userService.checkIfEmailAvailable(checkEmailRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(isAvailable);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        String username = userService.login(loginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(username);
    }
}