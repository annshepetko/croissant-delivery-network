package com.delivery.order.service;

import com.delivery.order.service.impl.order.AuthorizedOrderProcessor;
import com.delivery.order.service.impl.order.SimpleOrderProcessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.OrderService;
import com.delivery.order.service.interfaces.OrderProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderClient orderClient;

    @Mock
    private AuthorizedOrderProcessor authorizedOrderProcessor;

    @Mock
    private SimpleOrderProcessor simpleOrderProcessor;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPerformOrderWithAuthorizedUser() {
        String token = "Bearer token";
        PerformOrderRequest performOrderRequest = createPerformOrderRequest();
        UserDto userDto = new UserDto("user@example.com", 10.0);
        Optional<UserDto> userOptional = Optional.of(userDto);

        when(orderClient.getUserId(token)).thenReturn(new ResponseEntity<>(userOptional, HttpStatusCode.valueOf(200))); // <-- Correct response

        orderService.performOrder(performOrderRequest, token);

        verify(simpleOrderProcessor, times(0)).processOrder(performOrderRequest, userOptional);
        verify(authorizedOrderProcessor, times(1)).processOrder(performOrderRequest, userOptional); // <-- This should now work
    }

    @Test
    void testPerformOrderWithUnauthorizedUser() {
        String token = "Bearer token";
        PerformOrderRequest performOrderRequest = createPerformOrderRequest();
        Optional<UserDto> emptyUser = Optional.empty();

        when(orderClient.getUserId(token)).thenReturn(new ResponseEntity<>(emptyUser, HttpStatus.OK));

        orderService.performOrder(performOrderRequest, token);

    }



    private PerformOrderRequest createPerformOrderRequest() {
        return new PerformOrderRequest(
                List.of(),
                "John",
                "Doe",
                "+380670123456",
                null,
                0.0,
                false
        );
    }
}

