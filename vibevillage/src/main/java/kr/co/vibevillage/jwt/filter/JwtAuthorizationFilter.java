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
public class JwtAuthorizationFilter extends GenericFilterBean { // 요청이 들어올 때마다 JWT 토큰을 확인하고, 유효한 토큰이 있다면 인증 정보를 설정하여 사용자 권한을 관리하는 역할

    private static final String AUTHORIZATION_HEADER = "Authorization"; // 요청 헤더에서 JWT 토큰을 찾기 위한 헤더 이름
    private static final String BEARER_TYPE = "Bearer"; // JWT 토큰이 Bearer 타입임을 나타내는 접두사
    private static final String JWT_COOKIE_NAME = "JWT";  // 쿠키에서 JWT 토큰이 저장된 이름

    // JwtTokenProvider JWT 토큰 생성, 토큰 복호화 및 추출, 토큰 유효성 검증 기능을 하는 클래스
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();

        // 정적 리소스에 대한 요청은 로그를 남기지 않음
        if (requestURI.startsWith("/css/") || requestURI.startsWith("/js/") || requestURI.startsWith("/images/") || requestURI.startsWith("/lib/") || requestURI.startsWith("/scss/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // JWT 토큰 추출
        String token = resolveToken(request);

        if (token != null) {
            boolean isTokenValid = jwtTokenProvider.validateToken(token);

            if (isTokenValid) {
                // 토큰이 유효한 경우 로그 남기지 않음
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) {
                    SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
                    log.info("SecurityContext에 인증 정보가 설정되었습니다.");
                } else {
                    log.debug("SecurityContext에 이미 인증 정보가 설정되어 있습니다.");
                }
            } else {
                log.info("유효하지 않은 JWT 토큰이 요청에 포함되어 있습니다.");
            }
        } else {
            log.debug("JWT 토큰이 요청에 포함되어 있지 않습니다.");
        }

        // 다음 필터 체인으로 요청 전달
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // Request Header 또는 쿠키에서 토큰 정보 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (JWT_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
