package enstudy.signup.domain.user.service;

import enstudy.signup.domain.user.dto.request.LoginRequest;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    // 그냥 @Mock으로 할까?
    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private LoginRequest loginRequest;

    @Mock
    private User user;

    private final Faker faker = new Faker();

    public void successTestSetup() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String username = faker.name().firstName();
        String encodedPassword = passwordEncoder.encode(password);

        given(user.getPassword()).willReturn(encodedPassword);
        given(user.getUsername()).willReturn(username);

        given(loginRequest.email()).willReturn(email);
        given(loginRequest.password()).willReturn(password);
    }

    public void failTestSetup(){
        String email = faker.internet().emailAddress();
        String password1 = "1234";
        String password2 = "abcd";

        given(user.getPassword()).willReturn(password1);

        given(loginRequest.email()).willReturn(email);
        given(loginRequest.password()).willReturn(password2);
    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess(){
        // given
        successTestSetup();
        given(userRepository.findByEmail(loginRequest.email())).willReturn(Optional.of(user));

        // when
        String name = userService.login(loginRequest);

        // then
        Assertions.assertThat(name).isEqualTo(user.getUsername());
        // 인자까지 확인가능함??
        verify(passwordEncoder).matches(loginRequest.password(), user.getPassword());
    }

    @Test
    @DisplayName("로그인 실패 (이메일 오류)")
    public void loginFailByInvalidEmail(){
        // given
        given(loginRequest.email()).willReturn(faker.internet().emailAddress());
        given(userRepository.findByEmail(loginRequest.email())).willReturn(Optional.empty());

        // when & then
        Assertions.assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(UserException.class)
                .hasFieldOrPropertyWithValue("userErrorCode", UserErrorCode.INVALID_EMAIL);

        // then
        // 해당 함수 미호출 확인
        // 어차피 미호출되므로 함수 인자로는 와일드카드 사용
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("로그인 실패 (비밀번호 오류)")
    public void loginFailByInvalidPassword(){
        // given
        failTestSetup();
        given(userRepository.findByEmail(loginRequest.email())).willReturn(Optional.of(user));

        // when & then
        Assertions.assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(UserException.class)
                .hasFieldOrPropertyWithValue("userErrorCode", UserErrorCode.INVALID_PASSWORD);

        // then
        // verify는 해당 인자들로 호출되었는지까지 확인
        verify(passwordEncoder).matches(loginRequest.password(), user.getPassword());
    }
}
