package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginMapper loginMapper;

    // 로그인
    @Override
    public String login(String userId, String userPassword) {
        return loginMapper.login(userId, userPassword);
    }

    // 로그인한 사용자의 계정을 가져오는 메서드
    public String getLoginUserId() {
        // 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자의 정보를 User 객체로 변환
            User user = (User) authentication.getPrincipal();
            return user.getUsername(); // 사용자 이름 반환
        } else {
            log.info("로그인 상태를 확인해주세요.");
            return null;
        }
    }

    // 로그인한 유저 계정으로 데이터베이스에서 정보 가져오기
    public UserDTO getLoginUserInfo() {
        String loginUserId = getLoginUserId();
        return loginMapper.getLoginUserInfo(loginUserId);
    }

    // 로그인시 방문횟수 증가
    @Override
    public void addAccessCount(int userNo) {
        loginMapper.addAccessCount(userNo);
    }
}
