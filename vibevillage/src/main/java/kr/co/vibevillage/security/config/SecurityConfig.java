package kr.co.vibevillage.security.config;

import kr.co.vibevillage.jwt.filter.JwtAuthorizationFilter;
import kr.co.vibevillage.jwt.filter.LoginPageFilter;
import kr.co.vibevillage.jwt.provider.JwtTokenProvider;
import kr.co.vibevillage.user.model.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j  // 로그 출력을 위한 Lombok 어노테이션
@Configuration
@EnableWebSecurity  // Spring Security를 활성화하는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {

    // 커스텀 OAuth2 사용자 서비스, OAuth2 로그인 시 사용자 정보 처리
    private final OAuth2UserService oAuth2UserService;

    // jwt.secret 값을 환경변수에서 가져옴
    @Value("${jwt.secret}")
    private String secretKey;

    // KAKAO_LOGIN_API_KEY 값을 환경 변수에서 가져옴
    @Value("${KAKAO_LOGIN_API_KEY}")
    private String kakaoClientId;

    // KAKAO_CLIENT_SECRET_KEY 값을 환경 변수에서 가져옴
    @Value("${KAKAO_CLIENT_SECRET_KEY}")
    private String kakaoClientSecret;

    // JWT 토큰을 생성하고 검증하는 프로바이더를 Bean으로 등록
    @Bean
    public JwtTokenProvider jwtTokenProvider() {
        return new JwtTokenProvider(secretKey);
    }

    // 비밀번호 암호화를 위한 PasswordEncoder를 Bean으로 등록
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 카카오 OAuth2 클라이언트 등록 정보를 Bean으로 등록
    @Bean
    public ClientRegistration kakaoClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .redirectUri("{baseUrl}/oauth2/callback/kakao")
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .userNameAttributeName("id")
                .clientName("Kakao")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("profile_nickname")
                .build();
    }

    // ClientRegistrationRepository를 Bean으로 등록
    // InMemoryClientRegistrationRepository를 사용하여 카카오 클라이언트 등록 정보를 메모리에 저장
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(kakaoClientRegistration());
    }

    //  OAuth2AuthorizedClientService를 Bean으로 등록
    //  OAuth2 클라이언트와 사용자 간의 매핑 관리
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    // Spring Security의 필터 체인을 구성하는 메서드
    // JWT 인증 및 OAuth2 로그인을 포함한 다양한 보안 설정을 적용
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 특정 경로에 대해 인증을 요구하지 않도록 설정
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/lib/**", "/scss/**", "/videos/**").permitAll()
                        .requestMatchers("/form/login").permitAll()
                        .requestMatchers("/form/findUserInfo").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/oauth2/authorization/kakao").permitAll()
                        .requestMatchers("/oauth2/callback/kakao").permitAll()
                        .requestMatchers("/form").permitAll()
                        .requestMatchers("/sendCertificationNumber").permitAll()
                        .requestMatchers("/certification").permitAll()
                        .requestMatchers("/checkId").permitAll()
                        .requestMatchers("/checkNickName").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/profile").permitAll()
                        .requestMatchers("/findUserId").permitAll()
                        .requestMatchers("/findUserPassword").permitAll()
                        .requestMatchers("/checkUserInfoByPhone").permitAll()
                        .requestMatchers("/checkUserInfoById").permitAll()
                        // 특정 경로에 대해 인증을 요구하도록 설정
                        .requestMatchers(
                                "/experienceBoard/**",
                                "/used/**",
                                "/chat/**",
                                "/customerService/**",
                                "/levelUp/**"
                        ).authenticated()
                        // 나머지 모든 요청에 대해 인증을 요구하도록 설정
                        .anyRequest().authenticated()
                )
                // CSRF 보호 기능 비활성화
                .csrf(csrf -> csrf.disable())
                // JWT 인증 필터 추가
                .addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
                // 로그인 페이지 필터 추가
                .addFilterBefore(new LoginPageFilter(jwtTokenProvider()), UsernamePasswordAuthenticationFilter.class)
                // 기본 로그인 페이지 설정하고, 성공 시 리다이렉트할 경로 설정
                .formLogin(form -> form
                        .loginPage("/form/login")
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("/form");
                        }).permitAll())
                // 로그아웃 설정 구성
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/form")
                        .deleteCookies("JWT", "AccessToken", "JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                        .addLogoutHandler((request, response, authentication) -> log.info("로그아웃이 성공적으로 처리되었습니다."))
                )
                // 세션 관리를 STATELESS로 설정하여 세션을 미사용
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // OAuth2 로그인 설정을 추가
        http
                .oauth2Login(oauth2 -> oauth2
                        // OAuth2 클라이언트 등록 리포지토리와 클라이언트 서비스 사용
                        .clientRegistrationRepository(clientRegistrationRepository())
                        .authorizedClientService(authorizedClientService())
                        .loginPage("/form/login")  // 로그인 페이지 설정
                        .defaultSuccessUrl("/form"));  // 로그인 성공 시 리다이렉트할 기본 URL 설정

        // 카카오 로그인 시 사용자 정보를 가져오기 위한 OAuth2 설정 추가
        http
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))  // 사용자 정보를 처리할 서비스 설정
                );

        // 최종적으로 설정된 SecurityFilterChain 객체 반환
        return http.build();
    }
}