package com.delivery.order.mapper;

import com.delivery.order.entity.Order;
import com.delivery.order.kafka.dto.OrderNotification;
import com.delivery.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationBuilder {

    private final OrderMapper orderMapper;

    public OrderNotification buildOrderNotification(Order order){

        return OrderNotification.builder()
                .orderId(order.getId())
                .email(order.getEmail())
                .products(orderMapper.mapToOrderedProductRequests(order.getOrderedProducts()))
                .totalPrice(order.getTotalPrice())
                .userPhoneNumber(order.getUserPhoneNumber())
                .build();
    }

}
