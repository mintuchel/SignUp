package enstudy.signup.domain.member.dto.request;

public record LoginRequest(
        String email,
        String password
) { }
