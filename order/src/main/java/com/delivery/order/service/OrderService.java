package com.delivery.order.service;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.mapper.OrderBodyMapper;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.factory.OrderProcessorFacadeFactory;
import com.delivery.order.service.factory.impl.OrderProcessorFacadeFactoryImpl;
import com.delivery.order.service.interfaces.facade.OrderProcessorFacade;
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
    private OrderProcessorFacadeFactory orderProcessorFacadeFactory;
    private final OrderBodyMapper orderBodyMapper;


    private Optional<UserDto> getUser(String token) {

        return orderClient.getUserId(token).getBody();
    }

    public void performOrder(OrderRequest orderRequest, String token) {

        Optional<UserDto> user = getUser(token);

        OrderProcessorFacade orderProcessor = orderProcessorFacadeFactory.getOrderProcessor(user);
        orderProcessor.handleOrder(createOrderBody(orderRequest, user));
    }

    private OrderBody createOrderBody(OrderRequest orderRequest, Optional<UserDto> user) {
        return orderBodyMapper.createOrderBody(orderRequest, handleUser(user));
    }

    private String handleUser(Optional<UserDto> user){
        return user.map(UserDto::email).orElse("empty");
    }

    @Autowired
    public void setOrderProcessorFacadeFactory(OrderProcessorFacadeFactoryImpl orderProcessorFactory) {
        this.orderProcessorFacadeFactory = orderProcessorFactory;
    }

}
