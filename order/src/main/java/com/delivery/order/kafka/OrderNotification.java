package com.delivery.order.kafka;

import com.delivery.order.dto.OrderProductRequest;

import java.math.BigDecimal;
import java.util.List;

public record OrderNotification (

        BigDecimal totalPrice,
        Long orderId,
        List<OrderProductRequest> products,
        String email

){
}
