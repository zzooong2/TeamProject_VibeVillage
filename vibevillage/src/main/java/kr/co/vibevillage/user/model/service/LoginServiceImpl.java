package kr.co.vibevillage.user.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.vibevillage.user.model.dto.UserDTO;
import kr.co.vibevillage.user.model.mapper.FindMapper;
import kr.co.vibevillage.user.model.mapper.LoginMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final LoginMapper loginMapper;
    private final RegisterServiceImpl registerService;
    private final FindMapper findMapper;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoRestApiKey;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoSecretKey;

    // 로그인
    @Override
    public String login(String userId, String userPassword) {
        return loginMapper.login(userId, userPassword);
    }

    // 로그인한 사용자의 계정을 가져오는 메서드
    public String getLoginUserId() {
        // 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자의 정보를 User 객체로 변환
            User user = (User) authentication.getPrincipal();
            return user.getUsername(); // 사용자 이름 반환
        } else {
            log.info("로그인 상태를 확인해주세요.");
            return null;
        }
    }

    // 로그인한 유저 계정으로 데이터베이스에서 정보 가져오기
    public UserDTO getLoginUserInfo() {
        String loginUserId = getLoginUserId();
        return loginMapper.getLoginUserInfo(loginUserId);
    }

    // 로그인시 방문횟수 증가
    @Override
    public void addAccessCount(int userNo) {
        loginMapper.addAccessCount(userNo);
    }


    public UserDTO kakaoLogin(Model model, String code) throws JsonProcessingException {
        System.out.println("kakaoCallback 메서드 실행");

        // POST 방식으로 key=value 데이터를 요청 (카카오쪽으로)
        RestTemplate rt = new RestTemplate();

        // HTTP POST를 요청할 때 보내는 데이터(body)를 설명해주는 헤더도 만들어 같이 보내줘야 한다.
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // body 데이터를 담을 오브젝트인 MultiValueMap를 만들어보자
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoRestApiKey);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);
        params.add("client_secret", kakaoSecretKey);

        // 요청하기 위해 헤더(Header)와 데이터(Body)를 합친다.
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // POST 방식으로 Http 요청한다. 그리고 response 변수의 응답 받는다.
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST, // 요청할 방식
                kakaoTokenRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        // 액세스 토큰을 추출합니다.
        String parsedRespString = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode parsedResp = objectMapper.readTree(parsedRespString);

        String accessToken = parsedResp.get("access_token").asText();

        // 사용자 정보 요청을 위한 헤더 설정
        HttpHeaders userInfoHeaders = new HttpHeaders();
        userInfoHeaders.add("Authorization", "Bearer " + accessToken);

        // 사용자 정보 요청
        HttpEntity<Void> userInfoRequest = new HttpEntity<>(userInfoHeaders);
        ResponseEntity<String> getkakaoNickName = rt.exchange(
                "https://kapi.kakao.com/v2/user/me", // https://{요청할 서버 주소}
                HttpMethod.GET, // 요청할 방식
                userInfoRequest, // 요청할 때 보낼 데이터
                String.class // 요청 시 반환되는 데이터 타입
        );

        // 응답 처리
        String parsedkakaoEmailString = getkakaoNickName.getBody();
        JsonNode parsedkakaoEmail = objectMapper.readTree(parsedkakaoEmailString);

        System.out.println("parsedResp : " + parsedResp);
        System.out.println("parsedkakaoEmail: " + parsedkakaoEmail);

        // 이메일 가져오기
        String kakaoId = parsedkakaoEmail.get("kakao_account").get("email").asText();
        System.out.println("Kakao Id: " + kakaoId);

        // Model에 토큰과 만료 시간 등을 담아 응답합니다. (기존 코드 유지)

        // 카카오 계정과 같은 계정으로 회원가입된 유저 있는지 확인
        int findUser = findUserByKakaoId(kakaoId);
        if (findUser == 1) {
            UserDTO existingUser = findMapper.getUserByKakaoId(kakaoId);
            return existingUser;  // 로그인 처리를 위해 UserDTO 객체 반환
        } else {
            // 카카오 계정으로 회원가입
            int result = setKakaoUserInfoToDB(kakaoId);
            UserDTO getKakaoUser = findMapper.getUserByKakaoId(kakaoId);

            // 로그인 처리 - 이미 존재하는 login 메서드 사용
            String token = login(getKakaoUser.getUserId(), getKakaoUser.getUserPassword());
            // JWT 쿠키 저장 등 필요한 추가 작업 수행
            return getKakaoUser;
        }
    }

    // 카카오 계정으로 가입된 유저가 있는지 확인하는 메서드
    public int findUserByKakaoId(String kakaoId) {
        int result = findMapper.findUserByKakaoId(kakaoId);
        return result;
    }

    // 카카오 로그인시 회원정보(닉네임)를 받아 데이터베이스에 저장하는 로직
    public int setKakaoUserInfoToDB(String kakaoEmail) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName("kakao");
        userDTO.setUserId(kakaoEmail);
        userDTO.setUserPassword("!Qwer");
        userDTO.setUserNickName("kakao");
        userDTO.setUserPhone("000-0000-0000");
        userDTO.setUserPostCode("00000");
        userDTO.setUserAddress("kakao");
        userDTO.setUserDetailAddress("kakao");
        userDTO.setUserExtraAddress("kakao");
        userDTO.setUserBirthDate("19000101");

        int retsult = registerService.register(userDTO);

        return retsult;
    }

}
