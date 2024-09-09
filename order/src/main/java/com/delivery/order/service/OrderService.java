package com.delivery.order.service;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.openFeign.clients.OrderClient;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.OrderProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class OrderService {

    private final OrderClient orderClient;

    @Qualifier
    private final OrderProcessor orderProcessor;
    private final OrderProcessor simpleOrderProcessor;

    public OrderService(OrderClient orderClient,
                        @Qualifier("authorizedOrderProcessor") OrderProcessor orderProcessor,
                        @Qualifier("simpleOrderProcessor") OrderProcessor simpleOrderProcessor
    ) {
        this.orderClient = orderClient;
        this.orderProcessor = orderProcessor;
        this.simpleOrderProcessor = simpleOrderProcessor;
    }

    private Optional<UserDto> getUser(String token) {

        return orderClient.getUserId(token).getBody();
    }

    public void performOrder(PerformOrderRequest performOrderRequest, String token) {

        Optional<UserDto> user = getUser(token);
        log.info("USER_EMAIL : " + user);

        if (user.isPresent()) {
            orderProcessor.processOrder(performOrderRequest, user);
        } else {
            simpleOrderProcessor.processOrder(performOrderRequest, user);
        }
    }

}
