package com.delivery.notification.kafka.consumer.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record EmailNotification(

        BigDecimal totalPrice,
        Long orderId,
        List<OrderProductRequest> products,
        String email
){
}

