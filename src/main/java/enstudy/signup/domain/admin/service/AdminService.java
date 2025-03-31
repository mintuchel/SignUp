package enstudy.signup.domain.admin.service;

import enstudy.signup.domain.user.dto.response.UserInfoResponse;
import enstudy.signup.domain.emailverification.entity.EmailVerification;
import enstudy.signup.domain.emailverification.repository.EmailVerificationRepository;
import enstudy.signup.domain.user.entity.User;
import enstudy.signup.domain.user.repository.UserRepository;
import enstudy.signup.global.exception.errorcode.EmailErrorCode;
import enstudy.signup.global.exception.errorcode.UserErrorCode;
import enstudy.signup.global.exception.exception.EmailException;
import enstudy.signup.global.exception.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    // 근데 지금 생각드는건데 왜 @RequiredArgsConstructor가 작동하려면 꼭 final로 정의해야하는걸까??
    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;

    @Transactional(readOnly = true)
    public List<UserInfoResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        // stream 이랑 map 도 공부해야해
        // 언제까지 미룰꺼야 이거
        // 공부할거 개많다 진짜,,
        // :: 이거도 공부해야함
        return users.stream()
                .map(UserInfoResponse::from) // User -> UserInfoResponse 변환
                .toList();
    }

    @Transactional
    public void deleteUserByEmail(String email) {

        // 여기서는 existsByEmail 보다 findByEmail 을 사용하는 것이 좋음
        // 만약 존재한다면 바로 user 객체를 userRepository.delete(user); 이렇게 사용하면 되기 때문

        User user = userRepository.findByEmail(email)
                // 해당 이메일 유저가 존재하지 않는다면
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    @Transactional
    public void deleteEmail(String email) {
        // 이메일 인증 테이블에 이메일이 존재하지 않는다면
        // 애초에 인증코드가 전송된 적이 없다는 뜻
        EmailVerification targetEmailVerification = emailVerificationRepository.findById(email)
                .orElseThrow(() -> new EmailException(EmailErrorCode.CODE_NOT_SENT));

        emailVerificationRepository.delete(targetEmailVerification);
    }
}
