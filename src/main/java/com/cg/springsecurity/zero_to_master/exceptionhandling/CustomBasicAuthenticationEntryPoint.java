package com.cg.springsecurity.zero_to_master.exceptionhandling;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        LocalDateTime currentTimeStamp=LocalDateTime.now();
        String message=(authException!=null&&authException.getMessage()!=null)?authException.getMessage():"Unauthorized";
        String path=request.getRequestURI();
/*
        response.setHeader("WWW-Authenticate", "Basic realm=\"" + this.realName() + "\"");
*/
        response.setHeader("eazybank-error-reason","Authentication Failed");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
/*
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
*/
        response.setContentType("application/json; charset=utf-8");
        String jsonResponse = String.format("{\"timestamp\":\"%s\",\"status\":\"%d,\"error\":\"%s,\"message\":\"%s\",\"path\":\"%s\"}",
                currentTimeStamp,HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),message,path);
        response.getWriter().write(jsonResponse);
    }
}
