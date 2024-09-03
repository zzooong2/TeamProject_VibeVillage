package kr.co.vibevillage.user.model.mapper;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegisterMapper {

    // 회원가입
    public int register(UserDTO userDTO);

//    // 카카오 계정으로 회원가입
//    public int registerByKakaoId(UserDTO userDTO);

    // 회원등급 생성
    public int registerLevel(int userNo);

    // 닉네임 중복검사
    public int checkNickName(String userNickName);

    // 계정 중복검사
    public int checkId(String userId);
}
