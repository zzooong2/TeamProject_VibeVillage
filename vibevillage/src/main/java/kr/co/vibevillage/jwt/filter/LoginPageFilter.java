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

@Component
@RequiredArgsConstructor
public class LoginPageFilter extends OncePerRequestFilter {

    // JWT 토큰을 관리하는 JwtTokenProvider 인스턴스를 주입받음
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (new AntPathRequestMatcher("/form/login").matches(request)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()
                    && !authentication.getPrincipal().equals("anonymousUser")) {
                // 로그인된 사용자가 로그인 페이지에 접근 시, 리디렉션을 방지하고 로그인된 상태에서 /form 페이지로 리디렉션
                response.sendRedirect("/form");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
