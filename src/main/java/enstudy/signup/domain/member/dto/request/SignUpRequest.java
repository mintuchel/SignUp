package enstudy.signup.domain.member.dto.request;

public record SignUpRequest(
    String email,
    String password,
    String username,
    String address
) { }