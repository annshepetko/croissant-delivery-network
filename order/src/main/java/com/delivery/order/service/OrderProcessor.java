package com.delivery.order.service;


import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.kafka.dto.OrderNotification;
import com.delivery.order.mapper.NotificationBuilder;
import com.delivery.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderProcessor {

    private final NotificationBuilder notificationBuilder;
    private final DiscountService discountService;
    private final OrderEntityService orderEntityService;

    public OrderNotification processOrder(
            PerformOrderRequest performOrderRequest,
            Optional<String> email
    ){

        BigDecimal discountedOrderPrice = discountService.calculateTotalPrice(performOrderRequest.orderProductRequests(), calculateDiscount(email));
        OrderMapper.OrderBody orderBody = new OrderMapper.OrderBody(performOrderRequest, email.get(), discountedOrderPrice);

        Order order = orderEntityService.saveOrder(orderBody);

        return notificationBuilder.buildOrderNotification(order);

    }

    private Double calculateDiscount(Optional<String> email){
        return email.isPresent() ? 1.5 : 0.0;
    }

}
