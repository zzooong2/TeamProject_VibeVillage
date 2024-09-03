package kr.co.vibevillage.user.controller;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.LoginServiceImpl;
import kr.co.vibevillage.user.model.service.MyPageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    // 마이페이지 기능 구현을 위한 myPageService 객체 생성
    private final MyPageServiceImpl myPageService;
    // 로그인 회원 정보를 가져오기 위한 loginService 객체 생성
    private final LoginServiceImpl loginService;
    // 비밀번호 비교를 위한 passwordEncoder 객체 생성
    private final PasswordEncoder passwordEncoder;

    // 정보 가져오기
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
            model.addAttribute("profileResult", profileResult);
            log.info("프로필 없음");
            return "user/myPage";
        }
    }

    // 프로필 사진 업로드
    @PostMapping("/uploadProfile")
    @ResponseBody
    public String uploadProfile(UserDTO userDTO, @RequestParam(value="uploadFileElement") MultipartFile uploadFile) {
        // 업로드 파일 있는지 확인하기
        UserDTO loginUser = loginService.getLoginUserInfo();// 로그인한 회원 정보 가져오기
        int loginUserNo = loginUser.getUserNo(); // 회원 번호 가져오기
        UserDTO profileResult = myPageService.getProfileInfo(loginUserNo); // 회원 프로필 정보 가져오기

        // 업로드 프로필 파일이 없다면
        if(profileResult == null) {
            uploadProfileMethod(userDTO, uploadFile);
        } else { // 업로드 파일이 있다면
            // 기존 파일 삭제 진행
            int result = myPageService.deleteProfile(loginUserNo);
            if (result == 1) {
                uploadProfileMethod(userDTO, uploadFile);
            } else {
                return "기존파일 삭제실패";
            }
        }
        return "업로드 성공";
    }

    @PostMapping("/editProfile")
    @ResponseBody
    public String editProfile(UserDTO userDTO) {
        // 로그인한 유저 정보 가져오기
        UserDTO loginUser = loginService.getLoginUserInfo();
        // userDTO 객체에 회원번호 초기화
        userDTO.setUserNo(loginUser.getUserNo());

        int result = myPageService.updateProfile(userDTO);
            if (result == 1) {
                return "성공";
            } else {
                return "실패";
            }
    }

    @ResponseBody
    @PostMapping("/editPassword")
    public String editPassword(UserDTO userDTO) {
        // 로그인한 유저 정보 가져오기
        UserDTO loginUser = loginService.getLoginUserInfo();
        // 데이터베이스에서 암호화된 비밀번호 가져오기
        String getPassword = loginUser.getUserPassword();
        // userDTO 객체에 회원번호 초기화
        userDTO.setUserNo(loginUser.getUserNo());

        if(userDTO.getCurrentPassword() == getPassword) {
            return "현재 비밀번호로는 변경할 수 없습니다.";
        } else if(!passwordEncoder.matches(userDTO.getCurrentPassword(), getPassword)) {
            return "현재 비밀번호가 일치하지 않습니다.";
        } else {
            int result = myPageService.updatePassword(userDTO);
            if (result == 1) {
                return "성공";
            } else {
                return "실패";
            }
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

    // 파일 업로드 메서드
    private String uploadProfileMethod(UserDTO userDTO, @RequestParam(value="uploadFileElement") MultipartFile uploadFile) {
        // 업로드 파일 있는지 확인하기
        UserDTO loginUser = loginService.getLoginUserInfo();// 로그인한 회원 정보 가져오기
        int loginUserNo = loginUser.getUserNo(); // 회원 번호 가져오기
        UserDTO profileResult = myPageService.getProfileInfo(loginUserNo); // 회원 프로필 정보 가져오기

        // 업로드할 파일 경로 설정
        String uploadPath = "C:\\DEV\\IntelliJ\\FINAL_PROJECT\\TeamProject_VibeVillage\\vibevillage\\src\\main\\resources\\static\\uploadUsedImages";
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

        // 가져온 회원번호를 userDTO 객체에 초기화
        userDTO.setUserNo(loginUserNo);

        log.info(userDTO.toString());

        // service 객체를 이용한 파일 업로드
        int result = myPageService.uploadProfile(userDTO, uploadFile);

        if (result == 1) {
            return "업로드 성공";
        } else {
            return "업로드 실패";
        }
    }
}
