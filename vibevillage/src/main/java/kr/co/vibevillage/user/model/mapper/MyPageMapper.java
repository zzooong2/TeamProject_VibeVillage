package kr.co.vibevillage.user.model.mapper;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {

    // 프로필 업로드
    public int uploadProfile(UserDTO userDTO);

    // 프로필 조회
    public UserDTO getProfileInfo(int loginUserNo);

    // 프로필 삭제
    public int deleteProfile(int loginUserNo);

    // 프로필 변경
    public int updateProfile(UserDTO userDTO);

    // 비밀번호 변경
    public int updatePassword(UserDTO userDTO);
}
