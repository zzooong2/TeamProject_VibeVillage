package kr.co.vibevillage.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import kr.co.vibevillage.jwt.config.JWTConfig;
import kr.co.vibevillage.jwt.provider.JwtTokenProvider;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;

import kr.co.vibevillage.user.model.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대한 생성자를 만들어줌
public class LoginController {

    // 서비스 객체 생성
    private final LoginServiceImpl loginService;
    // 비밀번호 비교를 위한 passwordEncoder 객체 생성
    private final PasswordEncoder passwordEncoder;
    // JWT 생성을 위한 객체 생성
    private final JWTConfig jwt;
    // AccessToken, RefreshToken 생성을 위한 객체 생성
    private final JwtTokenProvider jwtTokenProvider;
    // 로그인 유저 정보 가져오기 위한 객체 생성
    private final LoginServiceImpl loginServiceImpl;
    private final OAuth2UserService oAuth2UserService;

    @Value("${jwt.secret}")
    private String secretKey; // 토큰 생성시 포함하는 시크릿 키
    @Value("${jwt.expiration_time}")
    private Long expiredMs; // 유효 시간 86400000ms

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoRestApiKey; // 카카오 로그인 API KEY
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri; // 카카오 로그인 RedirectURI
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoSecretKey; // 카카오 로그인 토큰 관련 보안 코드

    @ResponseBody
    @PostMapping("/login")
    public String login(@RequestBody UserDTO userDTO, HttpServletResponse response, Model model) {
        log.info("--------------------------logincontroller-------------------------");
        String userId = userDTO.getUserId();
        String userPassword = userDTO.getUserPassword();

        // 데이터베이스에서 암호화된 비밀번호 가져오기
        String getPassword = loginService.login(userId, userPassword);

        // 기본 권한 설정
        List<String> authorities = new ArrayList<>();
        userDTO.setAuthorities(authorities);
        authorities.add("ROLE_USER"); // 기본적으로 "ROLE_USER" 권한을 추가

        // 평문 비밀번호와 암호화된 비밀번호 비교
        if (passwordEncoder.matches(userPassword, getPassword)) {

            // JWT 생성
            String token = jwt.createJwt(userDTO, secretKey, expiredMs);
            System.out.println("로그인 컨트롤러에서 JWT 생성함");
            System.out.println("expiredMs: " + expiredMs);
            System.out.println("JWT: " + token);

            // JWT로부터 Authentication 객체 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // Authentication 객체를 SecurityContextHolder에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // JWT 쿠키에 저장
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            // 로그인한 사용자의 계정정보를 가져오기 위해 getLoggedInUsername 메서드 호출
            String loginUserId = loginServiceImpl.getLoginUserId();
            model.addAttribute("loginUserId", loginUserId);
            // 로그인한 사용자의 프로필 정보를 가져오기 위해 getLoginUserInfo 메서드 호출
            UserDTO loginUser = loginServiceImpl.getLoginUserInfo();
            int loginUserNo = loginUser.getUserNo();
            loginService.addAccessCount(loginUserNo);

            return "로그인 성공";
        } else {
            return "로그인 실패";
        }
    }


    @GetMapping("/oauth2/callback/kakao")
    public String kakaoCallback(Model model,
                                @RequestParam(required = false) String code,
                                @RequestParam(required = false) String error,
                                HttpServletResponse response) throws JsonProcessingException {

        if (error != null) {
            return "redirect:/form/login";
        } else {
            UserDTO kakaoUser = loginService.kakaoLogin(model, code);
            if (kakaoUser == null) {
                return "redirect:/form/login";
            } else {
                return "redirect:/login";  // 성공 후 리다이렉트할 경로
            }
        }
    }

}

