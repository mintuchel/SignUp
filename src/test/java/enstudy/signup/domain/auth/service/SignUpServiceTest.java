package enstudy.signup.domain.auth.service;

import enstudy.signup.domain.auth.dto.request.SignUpRequest;
import enstudy.signup.domain.user.entity.User;
import enstudy.signup.domain.user.repository.UserRepository;
import enstudy.signup.global.exception.errorcode.UserErrorCode;
import enstudy.signup.global.exception.exception.UserException;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private User user;

    @Mock
    private SignUpRequest signUpRequest;

    private final Faker faker = new Faker();

    public void UserSetUp(){
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String username = faker.name().firstName();
        String streetAddress = faker.address().streetAddress();
        String detailAddress = faker.address().secondaryAddress();

        given(signUpRequest.email()).willReturn(email);
        given(signUpRequest.password()).willReturn(password);
        given(signUpRequest.username()).willReturn(username);
        given(signUpRequest.streetAddress()).willReturn(streetAddress);
        given(signUpRequest.detailAddress()).willReturn(detailAddress);
    }


    @Test
    @DisplayName("회원가입 성공")
    public void signUpSuccess(){
        // given
        UserSetUp();
        // 이메일 미중복 상황 가정
        given(userRepository.existsByEmail(signUpRequest.email())).willReturn(false);

        // when
        int id = authService.signUp(signUpRequest);

        // then
        Assertions.assertThat(id).isNotNull();
        // 함수 호출 여부 확인
        verify(passwordEncoder).encode(signUpRequest.password());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패")
    public void signUpFail(){
        // given
        given(signUpRequest.email()).willReturn(faker.internet().emailAddress());
        // 이메일 중복 상황 가정
        given(userRepository.existsByEmail(signUpRequest.email())).willReturn(true);

        // when & then
        Assertions.assertThatThrownBy(() -> authService.signUp(signUpRequest))
                .isInstanceOf(UserException.class)
                .hasFieldOrPropertyWithValue("userErrorCode",UserErrorCode.DUPLICATE_EMAIL);

        // then
        // 함수 미호출 여부 확인
        verify(userRepository, never()).save(any(User.class));
    }

//    @Test
//    @DisplayName("특정 유저 조회 성공")
//    public void getUserByEmailSuccess(){
//        // given
//        String targetEmail = faker.internet().emailAddress();
//
//        given(user.getEmail()).willReturn(targetEmail);
//        given(userRepository.findByEmail(targetEmail)).willReturn(Optional.of(user));
//
//        // when
//        UserInfoResponse response = authService.getUserByEmail(targetEmail);
//
//        // then
//        Assertions.assertThat(response.email()).isEqualTo(targetEmail);
//    }
}
