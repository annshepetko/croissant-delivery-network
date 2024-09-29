package com.delivery.order.controller;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @PostMapping
    public void orderProducts(
            @RequestBody PerformOrderRequest performOrderRequest,
            HttpServletRequest httpServletRequest
    ){
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        logger.info("Received order request. Authorization token: {}", token != null ? "Present" : "Missing");

        logger.debug("PerformOrderRequest details: {}", performOrderRequest);

        orderService.performOrder(performOrderRequest, token);

        logger.info("Order processed successfully for user with token: {}", token != null ? "Present" : "Missing");
    }
}
