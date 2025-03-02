package enstudy.signup.domain.email.repository;

import enstudy.signup.domain.email.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    Optional<Email> findByEmail(String email);
}
