package kr.co.vibevillage.jwt.config;

import io.jsonwebtoken.*;
import kr.co.vibevillage.user.model.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JWTConfig {

    // JWT 생성
    public String createJwt(UserDTO userDTO, String secretKey, Long expiredMs){
        List<String> authorities = userDTO.getAuthorities();

        return Jwts.builder()
                .setSubject(userDTO.getUserId())
                .claim("auth", String.join(",", authorities)) // 권한 정보 추가
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
