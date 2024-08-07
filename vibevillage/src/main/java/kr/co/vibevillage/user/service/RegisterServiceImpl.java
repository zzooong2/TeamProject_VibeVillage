package kr.co.vibevillage.user.service;

import kr.co.vibevillage.user.dto.UserDTO;
import kr.co.vibevillage.user.mapper.RegisterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대한 생성자를 만들어줌
public class RegisterServiceImpl implements RegisterService{

    // Mapper 객체 생성
    private final RegisterMapper registerMapper;

    // 회원가입
    @Override
    public int register(UserDTO userDTO) {
        return registerMapper.register(userDTO);
    }

    // 닉네임 중복검사
    @Override
    public int checkNickName(String userNickName){
        return registerMapper.checkNickName(userNickName);
    }

    // 계정 중복검사
    @Override
    public int checkId(String userId) {
        return registerMapper.checkId(userId);
    }
}
