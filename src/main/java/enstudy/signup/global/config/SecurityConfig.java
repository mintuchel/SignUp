package enstudy.signup.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/user/signup").permitAll() // 회원가입 공개
                        .requestMatchers("/api/v1/user/login").permitAll()  // 로그인 공개 (필요 시)
                        .requestMatchers("/api/v1/user/checkEmail").permitAll()
                        .anyRequest().authenticated()                         // 나머지는 인증 필요
                )
                .csrf(csrf -> csrf.disable()); // CSRF 비활성화 (테스트 용이성)
        return http.build();
    }
}
