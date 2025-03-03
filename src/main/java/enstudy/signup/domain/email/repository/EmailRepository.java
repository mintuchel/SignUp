package enstudy.signup.domain.email.repository;

import enstudy.signup.domain.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE email SET code = :code WHERE email = :email", nativeQuery = true)
    void updateCodeById(@Param("email") String email, @Param("code") String code);
}
