package com.delivery.order.service;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.mapper.OrderBodyService;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.factory.OrderProcessorFactory;
import com.delivery.order.service.factory.impl.OrderProcessorFactoryImpl;
import com.delivery.order.service.interfaces.OrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderClient orderClient;
    private OrderProcessorFactory orderProcessorFactory;
    private final OrderBodyService orderBodyService;


    private Optional<UserDto> getUser(String token) {

        return orderClient.getUserId(token).getBody();
    }

    public void performOrder(OrderRequest orderRequest, String token) {

        Optional<UserDto> user = getUser(token);

        OrderProcessor orderProcessor = orderProcessorFactory.getOrderProcessor(user);
        orderProcessor.processOrder(createOrderBody(orderRequest, user));
    }

    private OrderBody createOrderBody(OrderRequest orderRequest, Optional<UserDto> user) {
        return orderBodyService.createOrderBody(orderRequest, handleUser(user));
    }

    private String handleUser(Optional<UserDto> user){
        return user.map(UserDto::email).orElse("empty");
    }

    @Autowired
    public void setOrderProcessorFactory(OrderProcessorFactoryImpl orderProcessorFactory) {
        this.orderProcessorFactory = orderProcessorFactory;
    }

}
