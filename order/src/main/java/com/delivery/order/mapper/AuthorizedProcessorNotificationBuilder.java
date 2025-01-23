package com.delivery.order.mapper;

import com.delivery.order.entity.Order;
import com.delivery.order.kafka.notification.EmailNotification;
import com.delivery.order.kafka.notification.UserNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorizedProcessorNotificationBuilder {

    private final OrderMapper orderMapper;



    public EmailNotification buildOrderNotification(Order order){

        return EmailNotification.builder()
                .orderId(order.getId())
                .email(order.getEmail())
                .products(orderMapper.mapToOrderedProductRequests(order.getOrderedProducts()))
                .totalPrice(order.getTotalPrice())
                .build();
    }



    public UserNotification buildUserNotification(Order order, Double bonuses){

        return com.delivery.order.kafka.notification.UserNotification.builder()
                .orderId(order.getId())
                .bonuses(bonuses)
                .email(order.getEmail())
                .build();
    }


}
