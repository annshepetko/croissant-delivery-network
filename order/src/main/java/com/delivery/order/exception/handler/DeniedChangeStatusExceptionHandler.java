package com.delivery.order.exception.handler;

import com.delivery.order.exception.DeniedChangeStatusException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class DeniedChangeStatusExceptionHandler {


    @ExceptionHandler(DeniedChangeStatusException.class)
    public ResponseEntity<Map<String, Object>> handleChangeStatusException(DeniedChangeStatusException deniedChangeStatusException){
        Map<String, Object> credentials = new HashMap<>();

        credentials.put("date", LocalDateTime.now().toString());
        credentials.put("message", deniedChangeStatusException.getMessage());

        return ResponseEntity.badRequest().body(credentials);
    }

}
