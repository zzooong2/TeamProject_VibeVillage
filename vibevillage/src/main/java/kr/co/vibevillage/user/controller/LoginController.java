package kr.co.vibevillage.user.controller;

import kr.co.vibevillage.jwt.config.JWTConfig;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @GetMapping("/login")
    public String login(@RequestParam("userId") String userId,@RequestParam("userPassword") String userPassword) {
        System.out.println("--------------------------logincontroller-------------------------");

        // 계정을 이용하여 데이터베이스에 있는 암호화된 비밀번호를 가져온다.
        String getPassword = loginService.login(userId, userPassword);
        // matches 메소드를 이용하여 평문 비밀번호와 암호화된 비밀번호를 비교한다.
        if(passwordEncoder.matches(userPassword, getPassword)){
            jwt.createAccessToken(userId, userPassword);
            return "redirect:/form";
        } else {
            return null;
        }
    }
}
