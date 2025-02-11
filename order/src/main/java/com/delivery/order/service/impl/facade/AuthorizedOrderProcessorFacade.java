package com.delivery.order.service.impl.facade;

import com.delivery.order.dto.OrderBody;
import com.delivery.order.kafka.notification.CommonNotification;
import com.delivery.order.service.interfaces.NotifiableOrderProcessor;
import com.delivery.order.service.interfaces.OrderProcessor;
import com.delivery.order.service.interfaces.facade.OrderProcessorFacade;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AuthorizedOrderProcessorFacade implements OrderProcessorFacade {


    private final NotifiableOrderProcessor orderProcessor;


    @Override
    public void handleOrder(OrderBody orderBody) {
        orderProcessor.processOrder(orderBody);
        sendNotifications(orderProcessor.getNotifications());

    }

    private void sendNotifications(List<CommonNotification> notifications){
        notifications.forEach((notification) -> notification.send(notification.getNotificationService()));
    }

}
