package kr.co.vibevillage.jwt.provider;

import kr.co.vibevillage.user.model.dto.UserDTO;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Slf4j
public class JwtTokenProvider { // JWT 토큰 생성, 토큰 복호화 및 추출, 토큰 유효성 검증 기능
    // JWT 토큰의 클레임(Claims)에서 권한 정보를 저장할 때 사용하는 키. 이 키를 사용하여 권한 정보를 JWT 토큰에 저장하거나, 토큰에서 권한 정보를 추출할 때 활용
    private static final String AUTHORITIES_KEY = "auth";
    // HTTP Authorization 헤더에서 사용되는 인증 타입인 Bearer 타입을 나타내는 상수. JWT 토큰은 일반적으로 "Bearer {토큰}" 형식으로 사용되며, 이 상수는 그 타입을 명시한다.
    private static final String BEARER_TYPE = "Bearer";
    // Access Token의 유효 시간을 정의하는 상수. 이 시간이 지나면 토큰이 만료된다. (30분)
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 30 * 60 * 1000L;
    // Refresh Token의 유효 시간을 정의하는 상수. Access Token이 만료되었을 때, 새로운 Access Token을 발급받기 위해 사용. (7일)
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    // security의 Key 클래스를 이용한 객체 생성
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        // BASE64를 이용한 디코딩
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        // 주어진 비밀 키(secretKey)사용, HMAC-SHA 알고리즘을 위한 Key 객체를 생성
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public UserDTO.TokenResDto generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // refresh Token 생성
        String accessToken = Jwts.builder()
                .setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return UserDTO.TokenResDto.builder()
                .grantType(BEARER_TYPE)
                .refreshTokenExpirationTime(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }


    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        String userId = claims.getSubject();

        log.info("토큰에서 추출한 사용자 ID: {}", userId);

        // 클레임에서 권한 정보 가져오기
        String authorityClaim = claims.get(AUTHORITIES_KEY, String.class);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(authorityClaim.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        log.info("사용자의 권한 정보: {}", authorityClaim);

        UserDetails principal = new User(userId, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            log.info("JWT 토큰이 성공적으로 검증되었습니다. 만료 시간: {}", claims.getBody().getExpiration());
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // 주어진 JWT 액세스 토큰을 복호화하여 토큰에 포함된 클레임(Claims) 정보를 추출하는 메서드
    private Claims parseClaims(String accessToken) {
        try {
            // JWT 토큰을 복호화하고, 그 결과로 클레임(Claims) 객체를 반환
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            // JWT 토큰이 만료된 경우 ExpiredJwtException이 발생하므로, 이 예외가 발생하면 만료된 클레임을 반환
            return e.getClaims();
        }
    }

    // 주어진 JWT 액세스 토큰의 남은 유효시간을 계산하는 메서드.
    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간 추출
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();

        // 현재 시간
        Long now = new Date().getTime();

        // 만료 시간에서 현재 시간을 뺀 값을 반환, 즉 남은 유효시간을 계산하여 반환
        return (expiration.getTime() - now);
    }
}