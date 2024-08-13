package kr.co.vibevillage.user.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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
    String userLevel, accessTime;
    int accessCount, writeCount, commentCount;

}
