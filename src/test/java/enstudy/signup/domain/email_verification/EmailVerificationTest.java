package enstudy.signup.domain.email_verification;

import enstudy.signup.domain.emailverification.dto.VerificationCodeRequest;
import enstudy.signup.domain.emailverification.entity.EmailVerification;
import enstudy.signup.domain.emailverification.repository.EmailVerificationRepository;
import enstudy.signup.domain.emailverification.service.EmailVerificationService;
import net.datafaker.Faker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailVerificationTest {
    @InjectMocks
    @Spy
    private EmailVerificationService emailVerificationService;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @Mock
    private VerificationCodeRequest verificationCodeRequest;

    private final Faker faker = new Faker();

    private String email;

    @BeforeEach
    public void testSetUp(){
        email = faker.internet().emailAddress();
        given(verificationCodeRequest.email()).willReturn(email);

        // 테스트 시 실제 메일 전송은 하지 않도록
        doNothing().when(emailVerificationService).sendVerificationCodeMail(any(), anyString());
    }

    // 최초로 이메일 인증 번호 전송
    // -> save 쿼리가 날라가야함
    @Test
    public void saveVerificationCodeSuccess(){
        // given
        // 해당 이메일이 테이블에 없는 것으로 설정
         given(emailVerificationRepository.existsById(email)).willReturn(false);

        // when
        emailVerificationService.sendVerificationCode(verificationCodeRequest);

        // then
        // save 함수가 호출되었는지 확인
        verify(emailVerificationRepository).save(any(EmailVerification.class));
    }

    // 기존에 테이블에 있던 회원이 인증번호 재요청
    // -> updateCodeById 쿼리가 날라가야함
    @Test
    public void updateVerificationCodeSuccess(){
        // given
        // 해당 이메일이 테이블에 존재한다고 설정
        given(emailVerificationRepository.existsById(email)).willReturn(true);

        // when
        emailVerificationService.sendVerificationCode(verificationCodeRequest);

        // then
        // update 함수가 호출되었는지 확인
        verify(emailVerificationRepository).updateCodeById(anyString(), anyString());
    }
}
