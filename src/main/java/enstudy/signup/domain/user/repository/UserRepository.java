package enstudy.signup.domain.user.repository;

import enstudy.signup.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

// email 에 인덱싱 걸어놓으면 좋을듯?

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    // 여기에도 Transactional이 사용되어야하는 것인지??
    @Modifying // 데이터를 변경하는 JPQL/Native Query를 수행
    @Transactional // @Modifying 메서드는 기본적으로 @Transactional이 필요함
    @Query(value = "UPDATE user_table SET password = :password WHERE email = :email", nativeQuery = true)
    int updatePasswordByEmail(@Param("email")String email, @Param("password")String password);
}
