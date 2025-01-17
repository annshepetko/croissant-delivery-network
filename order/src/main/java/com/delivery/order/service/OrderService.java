package com.delivery.order.service;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.impl.order.AuthorizedOrderProcessor;
import com.delivery.order.service.impl.order.SimpleOrderProcessor;
import com.delivery.order.service.interfaces.OrderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final OrderProcessor simpleOrderProcessor;
    private final OrderProcessor authorizedOrderProcessor;
    private final OrderClient orderClient;

    public OrderService(
            OrderClient orderClient,
            @Qualifier("simpleOrderProcessor")
            OrderProcessor orderProcessor,
            @Qualifier("authorizedOrderProcessor")
            OrderProcessor authorizedOrderProcessor
    ){
        this.authorizedOrderProcessor = authorizedOrderProcessor;
        this.simpleOrderProcessor = orderProcessor;
        this.orderClient = orderClient;
    }




    private Optional<UserDto> getUser(String token) {

        return orderClient.getUserId(token).getBody();
    }

    public void performOrder(PerformOrderRequest performOrderRequest, String token) {

        Optional<UserDto> user = getUser(token);
        if(user.isEmpty()){
            simpleOrderProcessor.processOrder(performOrderRequest, user);
        } else{
            authorizedOrderProcessor.processOrder(performOrderRequest, user);
        }
        log.info("USER_EMAIL : " + user);

    }

}
