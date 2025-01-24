package com.delivery.order.kafka.notification;

import com.delivery.order.service.interfaces.NotificationService;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserNotification implements CommonNotification{
    private String email;
    private Double bonuses;
    private Long orderId;
    private NotificationService notificationService;

    @Override
    public void send(NotificationService notificationService) {
        notificationService.send(this);
    }
}
