package enstudy.signup.domain.auth.repository;

import enstudy.signup.domain.user.entity.User;
import enstudy.signup.domain.user.repository.UserRepository;
import net.datafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    private final Faker faker = new Faker();

    @BeforeEach
    public void testSetUp(){
        user = User.builder()
                .email(faker.internet().emailAddress())
                .username(faker.name().firstName())
                .password(faker.internet().password())
                .streetAddress(faker.address().streetAddress())
                .detailAddress(faker.address().secondaryAddress())
                .build();
    }

    @Test
    public void saveUserSuccess() {
        // given

        // when
        userRepository.save(user);

        Assertions.assertThat(user.getId()).isNotNull();
    }
}
