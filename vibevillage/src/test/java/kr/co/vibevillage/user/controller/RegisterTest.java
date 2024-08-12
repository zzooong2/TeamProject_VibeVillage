package kr.co.vibevillage.user.controller;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.service.RegisterServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RegisterTest {

    @Autowired
    RegisterServiceImpl registerService;

    public UserDTO createUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserNo(1);
        userDTO.setUserName("김유저");
        userDTO.setUserId("user");
        userDTO.setUserPassword("qwer");
        userDTO.setUserNickName("관리자");
        userDTO.setUserPhone("00012345678");
        userDTO.setUserPostCode("13923");
        userDTO.setUserAddress("서울특별시 강남구 테헤란로 2");
        userDTO.setUserDetailAddress("그린 건물 2층");
        userDTO.setUserExtraAddress("(선릉)");
        userDTO.setUserIndate("2024-08-05");
        userDTO.setUserBirthDate("1994-03-02");
        userDTO.setUserStatus("N");
        userDTO.setUserLevel("가을");

        return userDTO;
    }

    @Test
    @DisplayName("회원가입 테스트")
    void 회원가입() {
        // given
        UserDTO user = createUser();
        int result = registerService.register(user);

        // when


        // then

    }
}
