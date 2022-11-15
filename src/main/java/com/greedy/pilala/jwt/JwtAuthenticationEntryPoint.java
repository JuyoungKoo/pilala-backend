package com.greedy.pilala.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.greedy.pilala.exception.dto.ApiExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ApiExceptionDto errorResponse = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, "인증되지 않은 유저 입니다.");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
