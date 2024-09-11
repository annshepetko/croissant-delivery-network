package com.delivery.order.controller;


import com.delivery.order.dto.OrderDto;
import com.delivery.order.service.OrderUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderUserController {

    private final OrderUserService orderUserService;

    @GetMapping("/all/{name}")
    public ResponseEntity<Optional<Page<OrderDto>>> getAllUserOrders(
            @PathVariable("name") String name,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue  = "5") Integer size
    ){

        Pageable pageableToPass = PageRequest.of(page, size);

        return ResponseEntity.ok(orderUserService.getAllUserOrders(name, pageableToPass));
    }
}
