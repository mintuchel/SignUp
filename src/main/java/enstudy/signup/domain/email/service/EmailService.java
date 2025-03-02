package enstudy.signup.domain.email.service;

import enstudy.signup.domain.email.dto.EmailRequest;
import enstudy.signup.domain.email.dto.EmailVerificationRequest;
import enstudy.signup.domain.email.entity.Email;
import enstudy.signup.domain.email.repository.EmailRepository;
import enstudy.signup.global.exception.errorcode.EmailErrorCode;
import enstudy.signup.global.exception.exception.EmailException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    private final JavaMailSender emailSender;

    private static final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public void sendCodeToEmail(EmailRequest emailRequest){
        String createdCode = createVerificationCode();
        sendCode(emailRequest, createdCode);

        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        // redisService.setValues(AUTH_CODE_PREFIX + toEmail, authCode, Duration.ofMillis(this.authCodeExpirationMillis));

        // 이미 존재한다면 최근 코드로 갱신
        boolean doesExist = emailRepository.existsById(emailRequest.email());

        if(doesExist){
            emailRepository.updateCodeById(emailRequest.email(), createdCode);
            return;
        }

        // 일단 Redis 안쓰고 DB에만 저장

        Email mail = Email.builder()
                .email(emailRequest.email())
                .code(createdCode)
                .build();

        emailRepository.save(mail);
    }

    private void sendCode(EmailRequest emailRequest, String createdCode){
        String title = "En# SignUp 이메일 인증 번호";

        String content = "<html>"
                + "<body>"
                + "<h1>이메일 인증 코드: " + createdCode + "</h1>"
                + "<p>해당 코드를 홈페이지에 입력하세요.</p>"
                + "<footer style='color: grey; font-size: small;'>"
                + "<p>※본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다.</p>"
                + "</footer>"
                + "</body>"
                + "</html>";

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailRequest.email());
            helper.setSubject(title);
            helper.setText(content, true);
            helper.setReplyTo("ensharp@gmail.com");

            emailSender.send(message);
        } catch (RuntimeException | MessagingException e) {
            // 메시지 전송 시 에러 터지면 서버 에러임
            throw new EmailException(EmailErrorCode.MESSAGING_ERROR);
        }
    }

    // SecureRandom 이용해서 6자리 인증코드 생성
    private String createVerificationCode() {
        return String.format("%06d", secureRandom.nextInt(1000000));
    }

    @Transactional(readOnly = true)
    public void verify(EmailVerificationRequest emailVerificationRequest) {

        Email email = emailRepository.findById(emailVerificationRequest.email())
                .orElseThrow(() -> new EmailException(EmailErrorCode.INVALID_EMAIL));

        if(!email.getCode().equals(emailVerificationRequest.code()))
            throw new EmailException(EmailErrorCode.INVALID_CODE);
    }
}
