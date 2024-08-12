package kr.co.vibevillage.user.model.service;

import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.mapper.RegisterMapper;
import kr.co.vibevillage.user.model.util.CertificationUtil;
import kr.co.vibevillage.user.redis.dao.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나 @NonNull이 붙은 필드에 대한 생성자를 만들어줌
public class RegisterServiceImpl implements RegisterService{

    // Mapper 객체 생성
    private final RegisterMapper registerMapper;
    // 문자인증을 위한 객체 생성
    private final CertificationUtil certificationUtil;
    // RedisRepository 객체 생성
    private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    @Override
    public int register(UserDTO userDTO) {

        String bcryptPassword = passwordEncoder.encode(userDTO.getUserPassword());
        userDTO.setUserPassword(bcryptPassword);

        int result = registerMapper.register(userDTO);

        if (result == 1) {
            int result2 = registerMapper.registerLevel(userDTO.getUserNo());
            return result2;
        } else {
            System.out.println("회원가입 에러발생");
            return 0;
        }
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

    // 문자인증
    @Override
    public void sendSms(String userPhone) {
        // 6자리 인증 코드를 랜덤으로 생성
        String certificationCode = Integer.toString((int)(Math.random() * (999999 - 100000 + 1)) + 100000);
        certificationUtil.sendSMS(userPhone, certificationCode);
        redisRepository.createSmsCertification(userPhone, certificationCode);
    }

    // 인증 번호 검증
    public String verify(String userPhone, String certificationInput) {
        boolean result = isVerify(userPhone, certificationInput);

        if(result) {
            return "인증실패";
        } else{
            return "인증성공";
        }

//        if (isVerify(userPhone, certificationInput)) {
//            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
//        } else {
//            return "인증 완료되었습니다.";
//        }
    }

    private boolean isVerify(String userPhone, String certificationInput) {
        return !(redisRepository.hasKey(userPhone) && redisRepository.getSmsCertification(userPhone).equals(certificationInput));
    }
}