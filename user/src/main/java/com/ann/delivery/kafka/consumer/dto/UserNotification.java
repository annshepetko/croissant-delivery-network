package com.ann.delivery.kafka.consumer.dto;


public record UserNotification (
        String email,
        Double bonuses,
        Long orderId
) {
}
