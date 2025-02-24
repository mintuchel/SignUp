package enstudy.signup.domain.member.service;

import enstudy.signup.domain.member.dto.request.LoginRequest;
import enstudy.signup.domain.member.dto.request.SignUpRequest;
import enstudy.signup.domain.member.entity.Member;
import enstudy.signup.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public int signUp(SignUpRequest signUpRequest){
        Member member = Member.builder()
                .email(signUpRequest.email())
                .username(signUpRequest.username())
                .password(signUpRequest.password())
                .build();

        memberRepository.save(member);

        return member.getId();
    }

    // 1. email password 일치 확인
    @Transactional(readOnly = true)
    public void login(LoginRequest loginRequest){

    }

}
