package com.delivery.notification.kafka.consumer.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmationKafkaMessage (
         BigDecimal totalPrice,
         Long orderId,
         List<OrderProductNotificationKafkaPart> products,
         String email

){
}
