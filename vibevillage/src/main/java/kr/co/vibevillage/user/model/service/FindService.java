package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;

public interface FindService {

    // 연락처로 회원 유무 확인하기
    public int checkUserInfoByPhone(String userPhone);

    // 계정 찾기
    public UserDTO findUserId(UserDTO userDTO);
}
