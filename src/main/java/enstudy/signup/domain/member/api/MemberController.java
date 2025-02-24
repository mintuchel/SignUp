package enstudy.signup.domain.member.api;

import enstudy.signup.domain.member.dto.request.LoginRequest;
import enstudy.signup.domain.member.dto.request.SignUpRequest;
import enstudy.signup.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public int signup(SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public void login(LoginRequest loginRequest) {
        memberService.login(loginRequest);
    }
}
