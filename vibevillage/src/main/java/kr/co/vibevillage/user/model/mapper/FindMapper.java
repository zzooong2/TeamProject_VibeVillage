package kr.co.vibevillage.user.model.mapper;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FindMapper {

    // 연락처로 회원 유무 확인하기
    public int checkUserInfoByPhone(String userPhone);

    // 계정으로 회원 유무 확인하기
    public int checkUserInfoById(String userId);

    // 계정 찾기
    public UserDTO findUserId(UserDTO userDTO);

    // 비밀번호 찾기
    public UserDTO findUserPassword(UserDTO userDTO);
}
