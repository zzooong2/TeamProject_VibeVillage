package kr.co.vibevillage.user.model.mapper;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {

    // 프로필 업로드
    public int uploadProfile(UserDTO userDTO);

    // 프로필 조회
    public UserDTO getProfileInfo(int loginUserNo);
}
