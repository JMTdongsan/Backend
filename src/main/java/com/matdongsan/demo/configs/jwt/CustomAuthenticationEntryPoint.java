package com.matdongsan.demo.configs.jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 권한이 맞지 않을 때 403 오류 코드 반환
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: You do not have the required permissions.");
    }
}