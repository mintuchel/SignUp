package enstudy.signup.domain.user.service;

import enstudy.signup.domain.user.dto.request.LoginRequest;
import enstudy.signup.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoginRequest loginRequest;

    @BeforeEach
    public void testSetup() {

    }

    @Test
    @DisplayName("로그인 성공")
    public void loginSuccess(){

    }

    @Test
    @DisplayName("로그인 실패")
    public void loginFail(){

    }
}
