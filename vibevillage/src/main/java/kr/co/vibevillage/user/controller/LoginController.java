package kr.co.vibevillage.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import kr.co.vibevillage.jwt.config.JWTConfig;
import kr.co.vibevillage.jwt.provider.JwtTokenProvider;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대한 생성자를 만들어줌
public class LoginController {

    // 서비스 객체 생성
    private final LoginServiceImpl loginService;
    // 비밀번호 비교를 위한 passwordEncoder 객체 생성
    private final PasswordEncoder passwordEncoder;
    // Jason Web Token 생성을 위한 객체 생성
    private final JWTConfig jwt;
    // AccessToken, RefreshToken 생성을 위한 객체 생성
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration_time}")
    private Long expiredMs;

    @GetMapping("/login")
    public String login(@RequestParam("userId") String userId, @RequestParam("userPassword") String userPassword, HttpServletResponse response) {
        System.out.println("--------------------------logincontroller-------------------------");

        // dto 객체 생성
        UserDTO userDTO = new UserDTO();
        // 값 초기화
        userDTO.setUserId(userId);

        // 계정을 이용하여 데이터베이스에 있는 암호화된 비밀번호를 가져온다.
        String getPassword = loginService.login(userId, userPassword);
        // matches 메소드를 이용하여 평문 비밀번호와 암호화된 비밀번호를 비교한다.
        if(passwordEncoder.matches(userPassword, getPassword)){
            // JWT 생성
            String token = jwt.createJwt(userDTO, secretKey, expiredMs);
            System.out.println("JWT 생성: " + token);

            // JWT로부터 Authentication 객체를 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // TokenResDto 객체를 받아온다
            UserDTO.TokenResDto tokenResDto = jwtTokenProvider.generateToken(authentication);
            // Access Token을 token2에 저장한다
            String token2 = tokenResDto.getAccessToken();
            System.out.println("tokenResDto: " + tokenResDto);
            System.out.println("token2: " + token2);

            // JWT 담아서 쿠키 생성
            Cookie cookie = new Cookie("JWT", token2);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            return "redirect:form";
        } else {
            System.out.println("로그인 실패");
            return null;
        }
    }
}
