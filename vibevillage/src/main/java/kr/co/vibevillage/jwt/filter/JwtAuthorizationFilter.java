package kr.co.vibevillage.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import kr.co.vibevillage.jwt.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";
    private static final String JWT_COOKIE_NAME = "JWT";  // 쿠키에서 JWT 토큰이 저장된 이름

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 1. Request Header 에서 JWT 토큰 추출 (먼저 헤더에서 시도)
        String token = resolveToken((HttpServletRequest) servletRequest);

        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 유효한 토큰이라면, Authentication 객체 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // 3. SecurityContextHolder에 Authentication 객체를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.info("SecurityContextHolder contains authentication: " + SecurityContextHolder.getContext().getAuthentication());
        } else {
            log.info("Token is null or invalid");
        }

        // 4. 다음 필터 체인으로 요청 전달
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Request Header 또는 쿠키에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        // 1. 헤더에서 토큰 시도
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7); // "Bearer "를 제거하고 토큰만 반환
        }

        // 2. 쿠키에서 JWT 토큰을 시도
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    // 쿠키에서 JWT 토큰을 찾으면 반환
                    return cookie.getValue();
                }
            }
        }
        // 토큰이 없다면 null 반환
        return null;
    }
}
