package kr.co.vibevillage.user.model.mapper;

import kr.co.vibevillage.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginMapper {

    // 로그인
    public String login(@Param(value="userId") String userId, String userPassword);

    // 로그인 회원 정보 가져오기
    public UserDTO getLoginUserInfo(String loginUserId);

    // 로그인 할 때 방문횟수 증가
    public void addAccessCount(int loginUserNo);

}
