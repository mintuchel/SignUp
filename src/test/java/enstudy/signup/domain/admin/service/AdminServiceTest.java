package enstudy.signup.domain.admin.service;

import enstudy.signup.domain.admin.dto.response.UserInfoResponse;
import enstudy.signup.domain.email.entity.Email;
import enstudy.signup.domain.email.repository.EmailRepository;
import enstudy.signup.domain.user.entity.User;
import enstudy.signup.domain.user.repository.UserRepository;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private User user1, user2;

    @Mock
    private Email email;

    private final Faker faker = new Faker();

    @Test
    @DisplayName("특정 유저 조회 성공")
    public void getUserByEmailSuccess(){
        // given
        String targetEmail = faker.internet().emailAddress();

        given(user1.getEmail()).willReturn(targetEmail);
        given(userRepository.findByEmail(targetEmail)).willReturn(Optional.of(user1));

        // when
        UserInfoResponse response = adminService.getUserByEmail(targetEmail);

        // then
        Assertions.assertThat(response.email()).isEqualTo(targetEmail);
    }

    @Test
    @DisplayName("전체 유저 조회 성공")
    public void getUsersSuccess(){
        // given
        given(userRepository.findAll()).willReturn(List.of(user1, user2));

        // when
        List<UserInfoResponse> responseList = adminService.getAllUsers();

        // then
        Assertions.assertThat(responseList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 유저 삭제 성공")
    public void deleteUserSuccess(){
        // given
        String targetEmail = faker.internet().emailAddress();
        given(userRepository.findByEmail(targetEmail)).willReturn(Optional.of(user1));

        // when
        adminService.deleteUserByEmail(targetEmail);

        // then
        // userRepository.delete 가 호출되었는지 확인
        verify(userRepository).delete(user1);
    }

    @Test
    @DisplayName("특정 이메일 삭제 성공")
    public void deleteEmailSuccess(){
        // given
        String targetEmail = faker.internet().emailAddress();
        given(emailRepository.findById(targetEmail)).willReturn(Optional.of(email));

        // when
        adminService.deleteEmail(targetEmail);

        // then
        verify(emailRepository).delete(email);
    }
}
