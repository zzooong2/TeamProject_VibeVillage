package kr.co.vibevillage.security.config;

import kr.co.vibevillage.jwt.filter.JwtAuthorizationFilter;
import kr.co.vibevillage.jwt.filter.LoginPageFilter;
import kr.co.vibevillage.jwt.provider.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity // 스프링시큐리티 필터가 스프링 필터체인에 등록된다
public class SecurityConfig {

    // application.properties에 작성해둔 암호화된 secretKey
    @Value("${jwt.secret}")
    private String secretKey;

    // JwtTokenProvider: JWT 토큰 생성, 토큰 복호화 및 추출, 토큰 유효성 검증 기능을 하는 클래스
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secretKey);
    }

    // 비밀번호 암호화
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/lib/**", "/scss/**", "/videos/**").permitAll() // 정적 리소스에 대한 접근을 허용
                        .requestMatchers("/form/login").permitAll() // 로그인 페이지 접근 허용
                        .requestMatchers("/form/findUserInfo").permitAll() // 계정, 비밀번호 찾기 페이지 접근 허용
                        .requestMatchers("/login").permitAll() // 로그인 처리 URL 접근 허용
                        .requestMatchers("/form").permitAll() // 메인 페이지 접근 허용
                        .requestMatchers("/sendCertificationNumber").permitAll() // coolSMS
                        .requestMatchers("/certification").permitAll() // coolSMS
                        .requestMatchers("/checkId").permitAll() // 닉네임 중복검사
                        .requestMatchers("/checkNickName").permitAll() // 닉네임 중복검사
                        .requestMatchers("/register").permitAll() // 회원가입

                        // 성오 작업 영역
                        .requestMatchers("/experienceBoard").permitAll() // 경험 및 리뷰 게시판
                        .requestMatchers("/experienceBoard/new").permitAll() // 경험 및 리뷰 게시판 글 작성 url
                        .requestMatchers("/experienceBoard/write").permitAll() // 경험 및 리뷰 게시판 글 등록 url
                        // 성오 작업 영역

                        .requestMatchers("/profile").permitAll() // 회원정보 가져오기
                        .requestMatchers("/findUserId").permitAll() // 회원계정 찾기
                        .requestMatchers("/findUserPassword").permitAll() // 회원계정 찾기
                        .requestMatchers("/checkUserInfoByPhone").permitAll() // 회원계정 찾기
                        .requestMatchers("/checkUserInfoById").permitAll() // 비밀번호 찾기
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
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new LoginPageFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form -> form
                        .loginPage("/form/login") // 커스텀 로그인 페이지 설정
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/form"); // 로그인 성공 시 리다이렉트
                        }).permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리 URL 설정
                        .logoutSuccessUrl("/form") // 로그아웃 후 리다이렉트할 URL 설정
                        .deleteCookies("JWT", "AccessToken", "JSESSIONID") // 로그아웃 시 삭제할 쿠키들 설정
                        .invalidateHttpSession(true) // 로그아웃 시 세션 무효화
                        .clearAuthentication(true) // 로그아웃 시 인증 정보 제거
                        .permitAll()
                        .addLogoutHandler((request, response, authentication) -> log.info("로그아웃이 성공적으로 처리되었습니다."))
                )

//                .logout(logout -> logout
//                        .logoutUrl("/logout") // 로그아웃 처리 URL 설정
//                        .logoutSuccessUrl("/form/login") // 로그아웃 후 리다이렉트할 URL 설정
//                        .deleteCookies("JWT", "AccessToken", "JSESSIONID") // 로그아웃 시 삭제할 쿠키들 설정
//                        .invalidateHttpSession(true) // 로그아웃 시 세션 무효화
//                        .clearAuthentication(true) // 로그아웃 시 인증 정보 제거
//                        .permitAll())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 무상태 세션 설정
                );

        return http.build();
    }
}
