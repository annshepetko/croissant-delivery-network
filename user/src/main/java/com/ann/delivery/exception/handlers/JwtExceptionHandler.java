package com.ann.delivery.exception.handlers;


import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class JwtExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleJwtException(JwtException jwtException){

        Map<String, String> credentials =  new HashMap<>();

        credentials.put("refresh-endpoint", "/api/user/auth/refresh");
        credentials.put("message", jwtException.getMessage());

        return ResponseEntity.status(403).body(credentials);

    }

}
