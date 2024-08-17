package kr.co.vibevillage.jwt.config;

import io.jsonwebtoken.*;
import kr.co.vibevillage.user.model.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Slf4j
@Component
public class JWTConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    // JWT 생성
    public String createJwt(UserDTO userDTO, String secretKey, Long expiredMs){
        return Jwts.builder()
                .claim("userId", userDTO.getUserId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .compact();
    }
}
