package com.delivery.order.kafka.notification;

import lombok.Builder;

@Builder
public record UserNotification(

        String email,
        Double bonuses,
        Long orderId

){
}
