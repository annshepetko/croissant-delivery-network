package com.delivery.product.exception.handler;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, Object> handleEntityNotFoundException(EntityNotFoundException exception){

        Map<String, Object> credentials = new HashMap<>();

        credentials.put("message", exception.getMessage());
        credentials.put("occurredAt", LocalDateTime.now());

        return credentials;
    }
}
