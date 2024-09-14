package com.delivery.notification.dto.mail;

import com.delivery.notification.kafka.consumer.dto.OrderConfirmationKafkaMessage;
import com.delivery.notification.kafka.consumer.dto.OrderProductNotificationKafkaPart;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class OrderEmailNotification extends EmailNotification {

    public BigDecimal totalPrice;
    public Long orderId;
    public List<OrderProductNotificationKafkaPart> products;

    public OrderEmailNotification(OrderConfirmationKafkaMessage kafkaMessage){
        this.orderId = kafkaMessage.orderId();
        this.totalPrice = kafkaMessage.totalPrice();
        this.products = kafkaMessage.products();
        this.email = kafkaMessage.email();
    }


}

