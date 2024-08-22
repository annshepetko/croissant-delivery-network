package com.ann.delivery.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class SecurityExceptionsConfig {

    @Bean
    public AuthenticationEntryPoint unauthorizedHandler(HttpServletRequest request, HttpServletResponse response){
        return (req, res, authEx) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
