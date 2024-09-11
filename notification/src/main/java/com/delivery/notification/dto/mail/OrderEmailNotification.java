package com.delivery.notification.dto.mail;

import com.delivery.notification.dto.mail.EmailNotification;
import com.delivery.notification.kafka.consumer.dto.OrderProductRequest;
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
    public List<OrderProductRequest> products;
    private String email;
}

