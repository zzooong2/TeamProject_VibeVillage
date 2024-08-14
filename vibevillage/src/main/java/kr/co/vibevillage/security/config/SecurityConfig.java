package kr.co.vibevillage.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    // springframework.security에서 제공하는 패스워드인코더 인터페이스를 이용하여 객체 생성 후 Bean 등록
    // 회원가입 처리되는 비즈니스 로직에서 이 객체를 이용하여 비밀번호 암호화 진행
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 모든 페이지에 대한 접근권한 설정, 사이트 위변조 방지 해제
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/**"))
                .permitAll())
                .csrf((csrf) -> csrf.disable());

        return http.build();
    }
}
