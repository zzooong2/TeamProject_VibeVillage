package kr.co.vibevillage.user.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDTO {

    // USER_VIBEVILLAGE TABLE
    @NotNull
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]+$")
    String userName;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])[A-Za-z\\d]{5,}$")
    String userId;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{5,}$")
    String userPassword;

    @NotNull
    String userRePassword;

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,20}$")
    String userNickName;

    @NotNull
    @Pattern(regexp = "^\\d{11}$")
    String userPhone;

    @NotNull
    @Pattern(regexp = "^(19[0-9]{2}|20[0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$")
    String userBirthDate;

    int userNo;
    String userPostCode, userAddress, userDetailAddress, userIndate, userStatus, userExtraAddress, userDeleteDate, certificationCode;

    // USER_LEVEL_MANAGEMENT TABLE
    String userLevel, userAccessTime;
    int userAccessCount, userWriteCount, userCommentCount;


    // 권한 정보를 담는 필드 추가
    private List<String> authorities;

    // UsernamePasswordAuthenticationToken 클래스 구현
    public UsernamePasswordAuthenticationToken toAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(userId, userPassword);
    }

    // Getters and Setters
    public List<String> getAuthorities() {
        if (authorities == null) {
            authorities = new ArrayList<>(); // authorities가 null일 경우 빈 리스트로 초기화
        }
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Getter @Builder
    public static class TokenResDto {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }
}
