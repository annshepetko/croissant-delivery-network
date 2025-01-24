package com.delivery.order.service;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.factory.OrderProcessorFactory;
import com.delivery.order.service.factory.impl.OrderProcessorFactoryImpl;
import com.delivery.order.service.interfaces.OrderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final OrderClient orderClient;
    private OrderProcessorFactory orderProcessorFactory;

    public OrderService(OrderClient orderClient){
        this.orderClient = orderClient;
    }

    private Optional<UserDto> getUser(String token) {

        return orderClient.getUserId(token).getBody();
    }

    public void performOrder(OrderRequest orderRequest, String token) {
        Optional<UserDto> user = getUser(token);
        OrderProcessor orderProcessor = orderProcessorFactory.getOrderProcessor(user);
        orderProcessor.processOrder(orderRequest, user);
    }

    public void setOrderProcessorFactory(OrderProcessorFactoryImpl orderProcessorFactory) {
        this.orderProcessorFactory = orderProcessorFactory;
    }
}
