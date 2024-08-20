package kr.co.vibevillage.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.vibevillage.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // 이 클래스가 Spring의 Bean으로 등록되도록 하는 어노테이션
@RequiredArgsConstructor // final 필드에 대해 자동으로 생성자를 생성해주는 Lombok 어노테이션
public class LoginPageFilter extends OncePerRequestFilter {

    // JwtTokenProvider JWT 토큰 생성, 토큰 복호화 및 추출, 토큰 유효성 검증 기능을 하는 클래스
    // JWT 토큰을 관리하는 JwtTokenProvider 인스턴스를 주입받음
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // "/form/login" URL로 들어온 요청인지 확인하는 조건문
        // AntPathRequestMatcher를 이용해 특정 패턴의 URL과 매칭되는지 확인
        if (new AntPathRequestMatcher("/form/login").matches(request)) {

            // SecurityContextHolder에서 현재 인증된 사용자 정보를 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 사용자가 인증되었는지(로그인 상태인지)와 익명 사용자인지 확인하는 조건문
            if (authentication != null && authentication.isAuthenticated()
                    && !authentication.getPrincipal().equals("anonymousUser")) {

                // 사용자가 이미 로그인된 상태라면, 로그인 페이지에 접근하지 못하도록 메인 페이지("/form")로 리디렉션
                response.sendRedirect("/form");
                return;
            }
        }
        // 위의 조건에 해당되지 않는 경우(즉, 로그인되지 않은 상태에서 로그인 페이지에 접근하는 경우),
        // 요청을 다음 필터로 넘김
        filterChain.doFilter(request, response);
    }
}
