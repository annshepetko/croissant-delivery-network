package com.delivery.order.kafka.notification;

import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.service.interfaces.NotificationService;
import com.netflix.spectator.impl.PatternExpr;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public class EmailNotification implements CommonNotification {
    NotificationService notificationService;
    BigDecimal totalPrice;
    Long orderId;
    List<OrderProductDto> products;
    String email;


    @Override
    public void send(NotificationService notificationService) {
        notificationService.send(this);
    }
}
