package enstudy.signup.domain.emailverification.template;

public class EmailTemplate {

    public static final String VERIFICATION_MAIL_TITLE = "En# SignUp 인증 번호";
    public static final String VERIFICATION_MAIL_CONTENT = """
        <html>
        <body>
        <h1>이메일 인증 코드: %s</h1>
        <p>해당 코드를 홈페이지에 입력하세요.</p>
        <footer style='color: grey; font-size: small;'>
            <p>※본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다.</p>
        </footer>
        </body>
        </html>
        """;
}
