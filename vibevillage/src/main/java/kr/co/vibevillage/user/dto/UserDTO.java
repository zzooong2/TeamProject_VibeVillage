package kr.co.vibevillage.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    int userNo;
    String userName, userId, userPassword, userNickName, userPhone, userPostCode, userAddress, userDetailAddress, userExtraAddress, userIndate, userBirthDate, userLevel, userStatus;
}
