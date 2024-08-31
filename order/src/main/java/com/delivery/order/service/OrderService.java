package com.delivery.order.service;


import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.kafka.dto.OrderNotification;
import com.delivery.order.openFeign.clients.OrderClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderClient orderClient;
    private final OrderProcessor orderProcessor;


    private Optional<String> getUserEmail(String token){
        var userId = orderClient.getUserId(token).getBody();
        return userId;
    }

    public void performOrder(PerformOrderRequest performOrderRequest, String token){

        Optional<String> email = getUserEmail(token);
        log.info("USER_EMAIL : " + email);

        processOrderBasedOnUserStatus(email, performOrderRequest);

    }

    private OrderNotification processOrderBasedOnUserStatus(Optional<String> email, PerformOrderRequest performOrderRequest){

        return orderProcessor.processOrder(performOrderRequest, email);
    }

}
