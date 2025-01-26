package com.delivery.order.controller;


import com.delivery.order.dto.user.OrderPageUserDto;
import com.delivery.order.dto.user.OrderUserServiceDto;
import com.delivery.order.service.api.user.OrderUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order/user")
@RequiredArgsConstructor
public class OrderUserController {

    private final OrderUserService orderUserService;

    @GetMapping("/all/{name}")
    public ResponseEntity<Optional<Page<OrderUserServiceDto>>> getAllUserOrders(
            @PathVariable("name") String name,
            @RequestParam(value = "pageNumber", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue  = "5") Integer size
    ){

        Pageable pageableToPass = PageRequest.of(page, size);

        return ResponseEntity.ok(orderUserService.getAllUserOrders(name, pageableToPass));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderPageUserDto> getOrderForUser(@PathVariable("id") Long orderId){
        return ResponseEntity.ok(orderUserService.getOrderPageForUser(orderId));
    }
}
