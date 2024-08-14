package kr.co.vibevillage.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // 토큰이 필요없는 페이지
//        List<String> list = Arrays.asList(
//                "form/login",  // 로그인 페이지
//                "/css/**", // css
//                "/js/**", // js
//                "/images/**" // images
//        );
//
//        // 토큰이 필요없는 페이지의 경우 -> 로직 처리없이 다음 필터로 이동한다.
//        if (list.contains(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        
    }
}
