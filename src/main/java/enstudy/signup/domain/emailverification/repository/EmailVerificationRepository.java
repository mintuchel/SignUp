package enstudy.signup.domain.emailverification.repository;

import enstudy.signup.domain.emailverification.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, String> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE email_verification SET verification_code = :verification_code WHERE email = :email", nativeQuery = true)
    void updateCodeById(@Param("email") String email, @Param("verification_code") String verification_code);
}
