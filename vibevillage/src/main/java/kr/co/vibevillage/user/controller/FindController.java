package kr.co.vibevillage.user.controller;

import kr.co.vibevillage.user.exception.UserNotFoundException;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.FindServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class FindController {

    // 서비스 객체 생성
    private final FindServiceImpl findService;

    // 연락처로 회원 유무 확인하기
    @PostMapping("/checkUserInfoByPhone")
    @ResponseBody
    public int checkUserInfoByPhone(@RequestParam("userPhone") String userPhone) {
        int result = findService.checkUserInfoByPhone(userPhone);

        if (result == 1) {
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
        System.out.println("FindController의 findUserId메서드 실행");
        System.out.println("입력한 이름: " + userDTO.getUserName());
        System.out.println("입력한 연락처: " + userDTO.getUserPhone());
        System.out.println("컨트롤러: " + user);
        if (user != null && userDTO.getUserName().equals(user.getUserName()) && userDTO.getUserPhone().equals(user.getUserPhone())) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // 예외 처리 핸들러
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
