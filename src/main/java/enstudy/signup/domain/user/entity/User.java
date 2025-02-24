package enstudy.signup.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

// 이미 DTO 단에서 제약조건을 모두 검사하고 들어오기 때문에 Entity 단에서도 jakarta constraints로 제약조건을 확인할 필요가 없음
// GenerationType.UUID를 사용해도 되지만, 현 어플리케이션은 IDENTITY 방식이 더 적합하다고 판단

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    // 이메일
    @Column(nullable = false, unique = true, length = 30)
    private String email;

    // 비밀번호
    // encode 시 BCrypt로 60자 정도 길이의 해싱된 비번 반환. 여유있게 100자로 제한
    @Column(nullable = false, length = 100)
    private String password;

    // 닉네임
    @Column(nullable = false)
    private String username;

    // 주소
    @Column(nullable = false)
    private String address;
}
