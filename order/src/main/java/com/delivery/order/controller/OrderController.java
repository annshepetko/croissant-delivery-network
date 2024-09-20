package com.delivery.order.controller;


import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public void orderProducts(
            @RequestBody PerformOrderRequest performOrderRequest,
            HttpServletRequest httpServletRequest
    ){
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        orderService.performOrder(performOrderRequest, token);
    }
}
