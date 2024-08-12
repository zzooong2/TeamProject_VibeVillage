package kr.co.vibevillage.user.controller;

import jakarta.validation.Valid;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.RegisterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대한 생성자를 만들어줌
public class RegisterController {

    // 서비스 객체 생성
    private final RegisterServiceImpl registerService;

    // 회원가입
    @PostMapping("/register")
    @ResponseBody
    public String register(@Valid UserDTO userDTO,
                           BindingResult bindingResult){
        System.out.println("-------------Register Controller-------------");
        System.out.println(userDTO.toString());

        if(bindingResult.hasErrors()){
            return "유효성 검사 오류";
        } else {
            int result = registerService.register(userDTO);

            if(result == 1){
                return "성공";
            } else {
                return "실패";
            }
        }
    }

    // 닉네임 중복검사
    @GetMapping("/checkNickName")
    @ResponseBody
    public String checkNickName(String userNickName) {
        System.out.println("입력한 값: " + userNickName);
        int result = registerService.checkNickName(userNickName);

        if(result == 1) {
            return "중복";
        } else {
            return "가능";
        }
    }

    // 계정 중복검사
    @GetMapping("/checkId")
    @ResponseBody
    public String checkId(String userId) {
        System.out.println("입력한 값: " + userId);
        int result = registerService.checkId(userId);

        if(result == 1) {
            return "중복";
        } else {
            return "가능";
        }
    }

    // 문자 인증
    @PostMapping("/sendCertificationNumber")
    @ResponseBody
    public void certification(String userPhone) {
        registerService.sendSms(userPhone);
    }

    // 인증 시작
    @PostMapping("/certification")
    @ResponseBody
    public String startCertification(String userPhone,String certificationInput) {
        return registerService.verify(userPhone, certificationInput);
    }
}
