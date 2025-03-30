package enstudy.signup.global.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

// 이 코드로 설정한 환경 변수는 해당 Spring Boot 애플리케이션이 실행되는 동안에만 유효하고,
// 애플리케이션이 종료되면 환경 변수도 자동으로 사라짐
// System.setProperty 는 JVM 환경 변수로 설정되는거임! (OS 로컬 컴퓨터 자체 환경변수로 등록되는게 아님!)

//@Component
//public class EnvConfig {
//    @PostConstruct
//    public void loadEnv() {
//        Dotenv dotenv = Dotenv.load();
//        dotenv.entries().forEach(entry ->
//                System.setProperty(entry.getKey(), entry.getValue())
//        );
//    }
//}

@Configuration
@PropertySource("classpath:.env")
public class EnvConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

