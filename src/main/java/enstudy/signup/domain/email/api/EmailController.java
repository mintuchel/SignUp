package enstudy.signup.domain.email.api;

import enstudy.signup.domain.email.dto.EmailRequest;
import enstudy.signup.domain.email.dto.EmailVerificationRequest;
import enstudy.signup.domain.email.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email/verification")
@Tag(name = "이메일 인증 API", description = "이메일 인증 관련")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/request")
    @Operation(summary = "이메일 인증 번호 전송")
    public ResponseEntity<String> sendVerificationCode(@Valid @RequestBody EmailRequest emailRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(emailService.sendVerificationCode(emailRequest));
    }

    @PostMapping("/confirm")
    @Operation(summary = "이메일 인증")
    public ResponseEntity<String> verifyCode(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(emailService.verify(emailVerificationRequest));
    }
}
