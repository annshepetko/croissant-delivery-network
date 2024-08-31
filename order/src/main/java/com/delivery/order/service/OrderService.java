package com.delivery.order.service;


import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.kafka.OrderNotification;
import com.delivery.order.openFeign.clients.OrderClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderClient orderClient;
    private final AuthorizedOrderProcessor authorizedOrderProcessor;
    private final UnAuthorizedOrderProcessor unAuthorizedOrderProcessor;

    private Long getUserId(String token){
        var userId = orderClient.getUserId(token).getBody();
        return userId.get();
    }

    public void performOrder(PerformOrderRequest performOrderRequest, String token){

        Long userId = getUserId(token);
        log.info("USER_ID : " + userId);

    }

    private OrderNotification processOrderBasedOnUserStatus(Optional<String> email, PerformOrderRequest performOrderRequest){

        if (email.isPresent()) {
            authorizedOrderProcessor.processOrderFromAuthenticatedUser(performOrderRequest, userId);
        }
        else {
            unAuthorizedOrderProcessor.processOrderFromNonAuthenticatedUser(performOrderRequest);
        }
    }

}
