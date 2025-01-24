package com.delivery.order.service.interfaces.dto;

import com.delivery.order.dto.PerformOrderRequest;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderConfirmationBody {

    private final PerformOrderRequest performOrderRequest;
    private String email;
    private final BigDecimal price;

    public OrderConfirmationBody(PerformOrderRequest performOrderRequest, String email, BigDecimal price) {
        this.performOrderRequest = performOrderRequest;
        this.email = email;
        this.price = price;
    }


    public OrderConfirmationBody(PerformOrderRequest performOrderRequest, BigDecimal price) {
        this.performOrderRequest = performOrderRequest;
        this.price = price;
    }
}
