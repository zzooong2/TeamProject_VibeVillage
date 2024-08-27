package kr.co.vibevillage.user.controller;

import kr.co.vibevillage.user.exception.UserNotFoundException;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.FindServiceImpl;
import kr.co.vibevillage.user.model.service.MyPageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FindController {

    // 서비스 객체 생성
    private final FindServiceImpl findService;
    private final MyPageServiceImpl myPageService;

    // 연락처로 회원 유무 확인하기
    @PostMapping("/checkUserInfoByPhone")
    @ResponseBody
    public int checkUserInfoByPhone(@RequestParam("userPhone") String userPhone) {
        int result = findService.checkUserInfoByPhone(userPhone);

        if (result != 0) {
            System.out.println("연락처로 회원 유무 확인 완료");
            return result;
        } else {
            System.out.println("연락처로 회원 유무 확인 실패");
            return 0;
        }
    }

    // 계정 찾기
    @PostMapping("findUserId")
    @ResponseBody
    public ResponseEntity<UserDTO> findUserId(@RequestBody UserDTO userDTO) {
        UserDTO user = findService.findUserId(userDTO);

        if (user != null && userDTO.getUserName().equals(user.getUserName()) && userDTO.getUserPhone().equals(user.getUserPhone())) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 비밀번호 찾기
    @PostMapping("/findUserPassword")
    @ResponseBody
    public ResponseEntity<UserDTO> findUserPassword(@RequestBody UserDTO userDTO) {
        System.out.println("FindController findUserPassword 메서드 실행");

        // 입력받은 회원 계정으로 정보 존재하는지 다시한번 확인
        UserDTO user = findService.findUserPassword(userDTO);
        System.out.println("user객체: " + user);

        // 회원이 존재한다면
        if (user != null) {
            // 임시 비밀번호를 명시하는 문자열
            String temporaryKey = "TKtk";
            // 랜덤 문자열 생성
            String createTemporaryPassword =  RandomStringGenerator.generateRandomString(8);
            // 임시 비밀번호 설정
            String temporaryPassword = temporaryKey + createTemporaryPassword;
            System.out.println("임시 비밀번호: " + temporaryPassword);

            // user 객체에 임시비밀번호 초기화
            user.setUserPassword(temporaryPassword);

            // 임시 비밀번호로 비밀번호 변경
            int result = myPageService.updatePassword(user);
            System.out.println("임시비번으로 변경한 결과: " + result);

            if(result == 1) { // 변경 완료
                // 유저에게 보여주기 위해 user 객체에 임시 비밀번호 다시 초기화
                user.setUserPassword(temporaryPassword);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else { // 변경 실패
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 예외 처리 핸들러
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 임시비밀번호 생성 (랜덤문자열)
    public class RandomStringGenerator {

        // 대문자 알파벳을 포함하는 문자열
        private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // 소문자 알파벳을 포함하는 문자열
        private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        // 숫자를 포함하는 문자열
        private static final String DIGITS = "0123456789";
        // 특수문자를 포함하는 문자열
        private static final String SPECIALS = "~!@#$%^&*";

        // 보안적으로 안전한 랜덤 값 생성을 위한 SecureRandom 인스턴스 생성
        private static final SecureRandom RANDOM = new SecureRandom();

        // 문자열은 대문자, 소문자, 숫자, 특수문자를 각각 최소 하나씩 포함한다.
        public static String generateRandomString(int length) {
            // 문자열 길이가 4보다 작으면 예외 발생
            if (length < 4) {
                throw new IllegalArgumentException("모든 문자 유형을 포함하려면 길이가 4 이상이어야 합니다.");
            }

            // 생성될 문자열의 각 문자를 저장할 리스트 생성
            List<Character> password = new ArrayList<>();

            // 각 타입의 문자에서 하나씩 랜덤하게 선택하여 리스트에 추가
            password.add(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length())));  // 대문자 중에서 랜덤하게 선택
            password.add(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));  // 소문자 중에서 랜덤하게 선택
            password.add(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));        // 숫자 중에서 랜덤하게 선택
            password.add(SPECIALS.charAt(RANDOM.nextInt(SPECIALS.length())));    // 특수문자 중에서 랜덤하게 선택

            // 나머지 자리의 문자를 랜덤하게 채우기
            // 사용 가능한 모든 문자를 포함하는 문자열 생성
            String allAllowedChars = UPPERCASE + LOWERCASE + DIGITS + SPECIALS;
            // 나머지 자리 수만큼 반복하여 랜덤하게 선택된 문자를 리스트에 추가
            for (int i = 4; i < length; i++) {
                password.add(allAllowedChars.charAt(RANDOM.nextInt(allAllowedChars.length())));
            }

            // 리스트의 순서를 섞어 다양한 문자 조합을 생성
            Collections.shuffle(password, RANDOM);

            // 리스트를 문자열로 변환하여 반환
            StringBuilder sb = new StringBuilder();
            for (char c : password) {
                sb.append(c);
            }

            // 최종적으로 생성된 랜덤 문자열 반환
            return sb.toString();
        }
    }
}