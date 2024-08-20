package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;

public interface LoginService {

    // 로그인
    public String login(String userId, String userPassword);

    // 로그인한 유저 계정정보 가져오기
    public String getLoginUserId();

    // 로그인 유저 계정으로 데이터베이스에 있는 정보 가져오기
    public UserDTO getLoginUserInfo();
}
