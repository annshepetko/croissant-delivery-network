package com.delivery.order.service;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.OrderHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderService {


    private final OrderHandler orderHandler;
    private final OrderClient orderClient;

    public OrderService(OrderClient orderClient, @Qualifier("simpleOrderProcessor") OrderHandler orderHandler){
        this.orderClient = orderClient;
        this.orderHandler = orderHandler;
    }




    private Optional<UserDto> getUser(String token) {

        return orderClient.getUserId(token).getBody();
    }

    public void performOrder(PerformOrderRequest performOrderRequest, String token) {

        Optional<UserDto> user = getUser(token);
        log.info("USER_EMAIL : " + user);

        orderHandler.handle(performOrderRequest, user);
    }

}
