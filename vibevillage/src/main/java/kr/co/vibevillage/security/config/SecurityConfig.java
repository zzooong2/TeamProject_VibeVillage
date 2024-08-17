package kr.co.vibevillage.security.config;

import kr.co.vibevillage.jwt.filter.JwtAuthorizationFilter;
import kr.co.vibevillage.jwt.provider.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링시큐리티 필터가 스프링 필터체인에 등록된다
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secretKey);
    }

    // springframework.security에서 제공하는 패스워드인코더 인터페이스를 이용하여 객체 생성 후 Bean 등록
    // 회원가입 처리되는 비즈니스 로직에서 이 객체를 이용하여 비밀번호 암호화 진행
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/lib/**", "/scss/**").permitAll()  // static 안의 모든 폴더 허용
                        .requestMatchers("/form/login").permitAll()
                        .requestMatchers("/form").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .loginPage("/form/login")
                        .defaultSuccessUrl("/form", true) // 로그인 성공 시 메인 페이지로 리디렉션 설정
                        .permitAll())
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}