package com.ann.delivery.kafka.consumer.listeners;

import com.ann.delivery.kafka.consumer.dto.UserNotification;
import com.ann.delivery.services.kafka.UserOrderListenerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationListener {

    private final UserOrderListenerService userOrderListenerService;

    public UserNotificationListener(UserOrderListenerService userOrderListenerService) {
        this.userOrderListenerService = userOrderListenerService;
    }


    @KafkaListener(topics = "user-order-confirmation", groupId = "user-id")
    public void userOrderConfirmationListener(UserNotification message){

        userOrderListenerService.handleOrderConfirmation(message);

    }

}
