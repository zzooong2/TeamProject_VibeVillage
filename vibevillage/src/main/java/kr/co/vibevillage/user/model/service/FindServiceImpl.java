package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.mapper.FindMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindServiceImpl implements FindService{

    // mapper 객체 생성
    private final FindMapper findMapper;

    // 연락처로 회원 유무 확인하기
    public int checkUserInfoByPhone(String userPhone) {
        return findMapper.checkUserInfoByPhone(userPhone);
    }

    // 계정 찾기
    @Override
    public UserDTO findUserId(UserDTO userDTO) {
        return findMapper.findUserId(userDTO);
    }

}
