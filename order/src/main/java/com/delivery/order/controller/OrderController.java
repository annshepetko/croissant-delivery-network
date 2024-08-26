package com.delivery.order.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commission")
@RequiredArgsConstructor
public class OrderController {

    @PostMapping("/order")
    public void orderProducts(){

    }

}
