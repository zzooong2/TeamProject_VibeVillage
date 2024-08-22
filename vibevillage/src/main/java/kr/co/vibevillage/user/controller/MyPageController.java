package kr.co.vibevillage.user.controller;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import kr.co.vibevillage.user.model.service.MyPageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageServiceImpl myPageService;
    private final LoginServiceImpl loginService;

    @GetMapping("/getUserInfo")
    public String myPageForm(Model model) {
        // 로그인한 회원 정보 가져오기
        UserDTO result = loginService.getLoginUserInfo();

        // 회원 프로필 가져오기
        int loginUserNo = result.getUserNo(); // 회원 번호 가져오기

        UserDTO profileResult = myPageService.getProfileInfo(loginUserNo);
        if(profileResult != null) {
            model.addAttribute("result", result);
            model.addAttribute("profileResult", profileResult);
            log.info(profileResult.toString());
            return "user/myPage";
        } else {
            model.addAttribute("result", result);
            log.info("프로필 없음");
            return "user/myPage";
        }
    }

    @PostMapping("/uploadProfile")
    @ResponseBody
    public String uploadProfile(UserDTO userDTO, @RequestParam(value="uploadFileElement") MultipartFile uploadFile) {
        // 업로드할 파일 경로 설정
        String uploadPath = "/static/images/userProfile";
        // userDTO 객체에 경로 초기화
        userDTO.setUploadFilePath(uploadPath);

        log.info(uploadFile.getOriginalFilename());

        // 유니크한 파일명 생성하기
        String originalFileName = userDTO.getUploadFileName(); // 원본 파일명

        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date()); // 현재 날짜를 "yyyyMMdd" 형식으로 포맷팅
        String randomAlphabets = getRandomAlphabets(6); // 랜덤한 6개의 알파벳 생성
        String uniqueFileName = currentDate + randomAlphabets + originalFileName; // 새로운 유니크한 파일명 생성

        // 파일명을 userDTO에 설정
        userDTO.setUploadFileUniqueName(uniqueFileName);

        // 로그인한 회원 정보 가져오기
        UserDTO loginUser = loginService.getLoginUserInfo();
        int userNo = loginUser.getUserNo();

        // 가져온 회원번호를 userDTO 객체에 초기화
        userDTO.setUserNo(userNo);

        log.info(userDTO.toString());

        // service 객체를 이용한 파일 업로드
        int result = myPageService.uploadProfile(userDTO, uploadFile);

        if (result == 1) {
            return "업로드 성공";
        } else {
            return "업로드 실패";
        }
    }

    // 랜덤한 알파벳 문자열 생성 함수
    private String getRandomAlphabets(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            // 'a'부터 'z'까지의 랜덤한 알파벳을 추가
            char randomChar = (char) (random.nextInt(26) + 'a');
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
