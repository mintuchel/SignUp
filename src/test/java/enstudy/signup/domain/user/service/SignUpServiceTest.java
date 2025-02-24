package enstudy.signup.domain.user.service;

import enstudy.signup.domain.user.dto.request.SignUpRequest;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private User user;

    @Mock
    private SignUpRequest signUpRequest;

    private final Faker faker = new Faker();

    public void UserSetUp(){
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String username = faker.name().firstName();
        String address = faker.address().fullAddress();

        given(signUpRequest.email()).willReturn(email);
        given(signUpRequest.password()).willReturn(password);
        given(signUpRequest.username()).willReturn(username);
        given(signUpRequest.address()).willReturn(address);
    }


    @Test
    @DisplayName("회원가입 성공")
    public void signUpSuccess(){
        // given
        UserSetUp();
        // 이메일 미중복 상황 가정
        given(userRepository.existsByEmail(signUpRequest.email())).willReturn(false);

        // when
        int id = userService.signUp(signUpRequest);

        // then
        Assertions.assertThat(id).isNotNull();
        verify(passwordEncoder).encode(signUpRequest.password());
        verify(userRepository).save(any(User.class));
        System.out.println(id);
    }

    @Test
    @DisplayName("회원가입 실패")
    public void signUpFail(){
        // given
        given(signUpRequest.email()).willReturn(faker.internet().emailAddress());
        // 이메일 중복 상황 가정
        given(userRepository.existsByEmail(signUpRequest.email())).willReturn(true);

        // when
        Assertions.assertThatThrownBy(() -> userService.signUp(signUpRequest))
                .isInstanceOf(UserException.class)
                .hasFieldOrPropertyWithValue("userErrorCode",UserErrorCode.DUPLICATE_EMAIL);

        // then
        verify(userRepository, never()).save(any(User.class));
    }
}
