package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginMapper loginMapper;

    @Override
    public String login(String userId, String userPassword) {

        return loginMapper.login(userId, userPassword);
    }
}
