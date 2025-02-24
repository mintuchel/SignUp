package enstudy.signup.domain.member.service;

import enstudy.signup.domain.member.dto.request.LoginRequest;
import enstudy.signup.domain.member.dto.request.SignUpRequest;
import enstudy.signup.domain.member.entity.Member;
import enstudy.signup.domain.member.repository.MemberRepository;
import enstudy.signup.global.exception.errorcode.MemberErrorCode;
import enstudy.signup.global.exception.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public int signUp(SignUpRequest signUpRequest){

        // 이미 해당 이메일 유저가 존재한다면 예외 던지기
        if(memberRepository.existsByEmail(signUpRequest.email())){
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .email(signUpRequest.email())
                .username(signUpRequest.username())
                // 비밀번호는 인코딩하여 저장
                .password(passwordEncoder.encode(signUpRequest.password()))
                .build();

        memberRepository.save(member);

        return member.getId();
    }

    @Transactional(readOnly = true)
    public boolean checkIfEmailAvailable(String email){
        // 만약 이메일이 중복되었으면 예외 던지기
        if(memberRepository.existsByEmail(email)) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        return true;
    }

    @Transactional(readOnly = true)
    public boolean login(LoginRequest loginRequest) {

        Member member = memberRepository.findByEmail(loginRequest.email())
                // 해당 이메일 유저가 존재하지 않는다면
                .orElseThrow(() -> new MemberException(MemberErrorCode.INVALID_EMAIL));

        // 만약 비밀번호가 일치하지 않는다면
        if(!member.getPassword().equals(loginRequest.password()))
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);

        return true;
    }
}
