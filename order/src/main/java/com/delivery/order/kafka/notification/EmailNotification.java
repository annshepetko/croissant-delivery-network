package com.delivery.order.kafka.notification;

import com.delivery.order.dto.product.OrderProductDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record EmailNotification(

        BigDecimal totalPrice,
        Long orderId,
        List<OrderProductDto> products,
        String email

){
}
