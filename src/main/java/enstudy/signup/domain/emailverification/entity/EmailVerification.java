package enstudy.signup.domain.emailverification.entity;

import jakarta.persistence.*;
import lombok.*;

// Redis 사용하는 것을 모르니
// 일단 JPA를 통해 key-value 쌍으로 저장해두자

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "email_verification")
public class EmailVerification {
    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "verification_code", nullable = false)
    private String verificationCode;
}
