package kr.co.vibevillage.security.config;

import kr.co.vibevillage.jwt.filter.JwtAuthorizationFilter;
import kr.co.vibevillage.jwt.filter.LoginPageFilter;
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

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // HttpSecurity 객체를 사용해 웹 보안 설정을 구성
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/lib/**", "/scss/**").permitAll() // 정적 리소스에 대한 접근을 허용
                        .requestMatchers("/form/login").permitAll() // 로그인 페이지 접근 허용
                        .requestMatchers("/login").permitAll() // 로그인 처리 URL 접근 허용
                        .requestMatchers("/form").permitAll() // 메인 페이지 접근 허용
                        .requestMatchers(
                                "/experienceBoard/**",
                                "/used/**",
                                "/chat/**",
                                "/customerService/**",
                                "/levelUp/**"
                        ).authenticated() // 지정된 URL 패턴에 대한 접근은 인증이 필요함
                        .anyRequest().authenticated() // 모든 다른 요청은 인증이 필요함
                )
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
                .formLogin(form -> form
                        .loginPage("/form/login") // 커스텀 로그인 페이지 설정
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/form"); // 로그인 성공 시 리다이렉트
                        })
                        .permitAll()) // 로그인 관련 요청 허용
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리 URL 설정
                        .logoutSuccessUrl("/form/login") // 로그아웃 후 리다이렉트할 URL 설정
                        .deleteCookies("JWT", "AccessToken", "JSESSIONID") // 로그아웃 시 삭제할 쿠키들 설정
                        .invalidateHttpSession(true) // 로그아웃 시 세션 무효화
                        .clearAuthentication(true) // 로그아웃 시 인증 정보 제거
                        .permitAll()) // 로그아웃 관련 모든 요청 허용
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LoginPageFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}