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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration_time}")
    private Long expiredMs;

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
            log.info("JWT: " + token);

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
            log.info("loginUserId: " + loginUserId);

            // 로그인한 사용자의 프로필 정보를 가져오기 위해 getLoginUserInfo 메서드 호출
            UserDTO loginUser = loginServiceImpl.getLoginUserInfo();
            log.info("유저번호: " + loginUser.getUserNo());
            log.info("닉네임: " + loginUser.getUserNickName());
            log.info("계정: " + loginUser.getUserId());
            log.info("암호: " + loginUser.getUserPassword());
            log.info("등급: " + loginUser.getUserLevel());

            return "로그인 성공";
        } else {
            return "로그인 실패";
        }
    }
//    public String login(@RequestParam("userId") String userId, @RequestParam("userPassword") String userPassword,
//                        HttpServletResponse response, Model model) {
//        log.info("--------------------------logincontroller-------------------------");
//
//        // DTO 객체 생성 및 사용자 ID 설정
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserId(userId);
//
//        // 데이터베이스에서 암호화된 비밀번호 가져오기
//        String getPassword = loginService.login(userId, userPassword);
//
//        // 기본 권한 설정
//        List<String> authorities = new ArrayList<>();
//        userDTO.setAuthorities(authorities);
//        authorities.add("ROLE_USER"); // 기본적으로 "ROLE_USER" 권한을 추가
//
//        // 평문 비밀번호와 암호화된 비밀번호 비교
//        if (passwordEncoder.matches(userPassword, getPassword)) {
//            // JWT 생성
//            String token = jwt.createJwt(userDTO, secretKey, expiredMs);
//            log.info("JWT: " + token);
//
//            // JWT로부터 Authentication 객체 생성
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//
//            // Authentication 객체를 SecurityContextHolder에 설정
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // TokenResDto 객체 받아오기
//            UserDTO.TokenResDto tokenResDto = jwtTokenProvider.generateToken(authentication);
//
//            // JWT 쿠키에 저장
//            Cookie jwtCookie = new Cookie("JWT", token);
//            jwtCookie.setHttpOnly(true);
//            jwtCookie.setPath("/");
//            response.addCookie(jwtCookie);
//
//            // AccessToken 쿠키에 저장
//            Cookie accessTokenCookie = new Cookie("AccessToken", tokenResDto.getAccessToken());
//            accessTokenCookie.setHttpOnly(true);
//            accessTokenCookie.setPath("/");
//            response.addCookie(accessTokenCookie);
//
//            // 로그인한 사용자의 계정정보를 가져오기 위해 getLoggedInUsername 메서드 호출
//            String loginUserId = loginServiceImpl.getLoginUserId();
//            model.addAttribute("loginUserId", loginUserId);
//            log.info("loginUserId: " + loginUserId);
//
//            // 로그인한 사용자의 프로필 정보를 가져오기 위해 getLoginUserInfo 메서드 호출
//            UserDTO loginUser = loginServiceImpl.getLoginUserInfo();
//            log.info("유저번호: " + loginUser.getUserNo());
//            log.info("닉네임: " + loginUser.getUserNickName());
//            log.info("계정: " + loginUser.getUserId());
//            log.info("암호: " + loginUser.getUserPassword());
//            log.info("등급: " + loginUser.getUserLevel());
//
//            return "redirect:/form";
//        } else {
//            model.addAttribute("errorMessage", "계정, 비밀번호를 확인해주세요.");
//        }
//        return "/login";
//    }
}
