package com.delivery.order.controller;


import com.delivery.order.dto.OrderProductRequest;
import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commission")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/order")
    public void orderProducts(
            @RequestBody PerformOrderRequest performOrderRequest,
            HttpServletRequest httpServletRequest
    ){
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        orderService.performOrder(performOrderRequest, token);
    }
}
