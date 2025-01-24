package com.delivery.order.service.impl.order;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.kafka.notification.CommonNotification;
import com.delivery.order.mapper.AuthorizedProcessorNotificationBuilder;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.OrderProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorizedOrderProcessor  implements OrderProcessor {

    private final SimpleOrderProcessor simpleOrderProcessor;
    private final AuthorizedProcessorNotificationBuilder notificationBuilder;

    public AuthorizedOrderProcessor(

            @Qualifier("simpleOrderProcessor") OrderProcessor simpleOrderProcessor,
            AuthorizedProcessorNotificationBuilder authorizedProcessorNotificationBuilder
    ) {
        this.simpleOrderProcessor = (SimpleOrderProcessor) simpleOrderProcessor;
        this.notificationBuilder = authorizedProcessorNotificationBuilder;
    }

    @Override
    public Order processOrder(OrderRequest orderRequest, Optional<UserDto> user) {

        Order order = simpleOrderProcessor.processOrder(orderRequest, user);
        SimpleOrderProcessor.BonusWriteOff refreshedBonuses = getRefreshedBonuses(orderRequest);

        sendNotifications(order, refreshedBonuses);

        return order;
    }

    private SimpleOrderProcessor.BonusWriteOff getRefreshedBonuses(OrderRequest orderRequest) {
        return simpleOrderProcessor.buildBonusWriteOff(orderRequest);
    }

    private void sendNotifications(Order order, SimpleOrderProcessor.BonusWriteOff refreshedBonuses) {

        List<CommonNotification> listOfNotifications = createNotificationsToSend(order, refreshedBonuses);
        listOfNotifications.forEach(notification -> notification.send(notification.getNotificationService()));
    }

    private List<CommonNotification> createNotificationsToSend(Order order, SimpleOrderProcessor.BonusWriteOff refreshedBonuses) {

        return notificationBuilder.createNotifications(order, refreshedBonuses.getBonuses());
    }


}
