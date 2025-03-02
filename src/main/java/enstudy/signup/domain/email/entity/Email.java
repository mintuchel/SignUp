package enstudy.signup.domain.email.entity;

import jakarta.persistence.*;
import lombok.*;

// Redis 사용하는 것을 모르니
// 일단 JPA를 통해 key-value 쌍으로 저장해두자

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Email {
    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private String code;
}
