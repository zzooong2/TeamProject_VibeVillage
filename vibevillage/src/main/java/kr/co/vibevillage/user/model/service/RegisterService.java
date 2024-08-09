package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;

public interface RegisterService {

    // 회원가입
    public int register(UserDTO userDTO);

    // 닉네임 중복검사
    public int checkNickName(String userNickName);

    // 계정 중복검사
    public int checkId(String userId);

    // 문자인증
    public void sendSms(String userPhone);// 문자인증

}
