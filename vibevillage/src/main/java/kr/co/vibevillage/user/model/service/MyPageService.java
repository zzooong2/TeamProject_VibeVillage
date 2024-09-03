package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MyPageService {

    // 프로필 업로드
    public int uploadProfile(UserDTO userDTO, MultipartFile uploadFile);

    // 프로필 조회
    public UserDTO getProfileInfo(int loginUserNo);

    // 프로필 삭제
    public int deleteProfile(int loginUserNo);

    // 프로필 변경
    public int updateProfile(UserDTO userDTO);

    // 비밀번호 변경
    public int updatePassword(UserDTO userDTO);

    // 비밀번호 변경 (암호화 없는 임시 비밀번호)
    public int updatePasswordNoneProtected(UserDTO userDTO);
}
