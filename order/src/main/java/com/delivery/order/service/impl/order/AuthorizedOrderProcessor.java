package com.delivery.order.service.impl.order;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.kafka.notification.EmailNotification;
import com.delivery.order.kafka.notification.UserNotification;
import com.delivery.order.mapper.NotificationBuilder;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.NotificationService;
import com.delivery.order.service.interfaces.OrderHandler;
import com.delivery.order.service.interfaces.OrderProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorizedOrderProcessor extends OrderHandler implements OrderProcessor {

    private final NotificationService notificationService;
    private final SimpleOrderProcessor simpleOrderProcessor;
    private final NotificationBuilder notificationBuilder;
    private final NotificationService userNotificationService;

    public AuthorizedOrderProcessor(

            @Qualifier("emailNotificationService") NotificationService notificationService,
            @Qualifier("userNotificationService") NotificationService userNotificationService,
            @Qualifier("simpleOrderProcessor") OrderProcessor simpleOrderProcessor,
            NotificationBuilder notificationBuilder
    ) {
        super(null);
        this.notificationService = notificationService;
        this.simpleOrderProcessor = (SimpleOrderProcessor) simpleOrderProcessor;
        this.notificationBuilder = notificationBuilder;
        this.userNotificationService = userNotificationService;
    }

    @Override
    public void handle(PerformOrderRequest performOrderRequest, Optional<UserDto> userDto) {
        processOrder(performOrderRequest, userDto);
    }

    @Override
    public Order processOrder(PerformOrderRequest performOrderRequest, Optional<UserDto> user) {

        Order order = simpleOrderProcessor.processOrder(performOrderRequest, user);
        SimpleOrderProcessor.BonusWriteOff refreshedBonuses = simpleOrderProcessor.buildBonusWriteOff(performOrderRequest);

        EmailNotification emailNotification = notificationBuilder.buildOrderNotification(order);
        UserNotification userNotification = notificationBuilder.buildUserNotification(order, refreshedBonuses.getBonuses());

        notificationService.send(emailNotification);
        userNotificationService.send(userNotification);

        return order;
    }



}
