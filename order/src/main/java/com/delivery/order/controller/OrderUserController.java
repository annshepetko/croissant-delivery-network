package com.delivery.order.controller;


import com.delivery.order.dto.OrderDto;
import com.delivery.order.service.OrderUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderUserController {

    private final OrderUserService orderUserService;

    @GetMapping("/all/{name}")
    public ResponseEntity<Optional<List<OrderDto>>> getAllUserOrders(@PathVariable("name") String name){
        return ResponseEntity.ok(orderUserService.getAllUserOrders(name));
    }
}
