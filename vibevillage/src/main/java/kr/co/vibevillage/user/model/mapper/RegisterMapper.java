package kr.co.vibevillage.user.model.mapper;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {

    // 회원가입
    public int register(UserDTO userDTO);

    // 닉네임 중복검사
    public int checkNickName(String userNickName);

    // 계정 중복검사
    public int checkId(String userId);
}
