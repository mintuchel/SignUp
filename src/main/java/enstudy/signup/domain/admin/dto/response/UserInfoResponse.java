package enstudy.signup.domain.admin.dto.response;

import enstudy.signup.domain.user.entity.User;

// responsedto에 정적 팩토리 메서드 사용
// record 내부에 static으로 생성자같은 함수를 집어넣는 디자인 패턴
// new 또는 builder를 사용하지 않는 패턴이다
// 나도 지금은 개발하느라 왜 이런걸 쓰는지 이해는 안간다...

// of 랑 from 은 정적 팩토리 메서드에서 자주 쓰이는 네이밍 컨벤션이라고 한다
// of = 명시적인 값들을 기반으로 객체 생성
// from = 객체를 기반으로 생성
public record UserInfoResponse (
        String email,
        String password,
        String username,
        String streetAddress,
        String detailAddress
) {
    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getStreetAddress(),
                user.getDetailAddress()
        );
    }
}
