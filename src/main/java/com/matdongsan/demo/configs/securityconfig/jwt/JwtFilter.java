package com.matdongsan.demo.configs.securityconfig.jwt;

import com.matdongsan.demo.mysql.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (!StringUtils.hasText(bearerToken)) {
            bearerToken = request.getParameter("token");
        }

        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {

            log.info("토큰이 null이거나 jwt 토큰이 아닙니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String token = bearerToken.substring(7);

        if (jwtUtils.isExpired(token)) {

            log.info("토큰의 유효 기간이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        Member member = Member.builder()
                .username(jwtUtils.getUsername(token))
                .password("tempPassword")
                .role(jwtUtils.getRole(token))
                .build();

        CustomUserDetails userDetails = new CustomUserDetails(member);

        // 스프링 시큐리티 인증 토큰 생성, 사용자 세션에 등록
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePath = {"/signUp", "/login"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
