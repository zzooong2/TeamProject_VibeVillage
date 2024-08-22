package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MyPageService {

    // 프로필 업로드
    public int uploadProfile(UserDTO userDTO, MultipartFile uploadFile);

    // 프로필 조회
    public UserDTO getProfileInfo(int loginUserNo);
}
