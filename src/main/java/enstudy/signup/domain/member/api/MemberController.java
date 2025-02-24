package enstudy.signup.domain.member.api;

import enstudy.signup.domain.member.dto.request.LoginRequest;
import enstudy.signup.domain.member.dto.request.SignUpRequest;
import enstudy.signup.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController가 @ResponseBody를 포함하므로 리턴되는 객체가 자동으로 JSON으로 변환되며,
// 응답의 Content-Type이 기본적으로 application/json으로 설정됨

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Integer> signUp(SignUpRequest signUpRequest) {
        int id = memberService.signUp(signUpRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(id);
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<Boolean> checkIfEmailAvailable(String email) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(memberService.checkIfEmailAvailable(email));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(LoginRequest loginRequest) {
        boolean isValidLogin = memberService.login(loginRequest);

        if(isValidLogin) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
