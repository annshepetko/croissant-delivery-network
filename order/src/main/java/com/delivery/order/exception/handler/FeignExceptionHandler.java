package com.delivery.order.exception.handler;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class FeignExceptionHandler {


    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(FeignException feignException){

        Map<String, Object> credential = new HashMap<>();

        credential.put("error", "Access token has expired");
        credential.put("refresh", "/api/user/auth/refresh");


        return ResponseEntity.status(401).body(credential);
    }
}
