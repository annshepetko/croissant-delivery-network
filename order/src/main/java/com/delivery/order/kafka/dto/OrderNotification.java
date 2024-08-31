package com.delivery.order.kafka.dto;

import com.delivery.order.dto.OrderProductRequest;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderNotification (

        BigDecimal totalPrice,
        Long orderId,
        List<OrderProductRequest> products,
        String email,
        String userPhoneNumber

){
}
