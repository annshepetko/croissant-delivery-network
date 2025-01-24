package com.delivery.order.service.impl.order;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.kafka.notification.EmailNotification;
import com.delivery.order.kafka.notification.UserNotification;
import com.delivery.order.mapper.AuthorizedProcessorNotificationBuilder;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.NotificationService;
import com.delivery.order.service.interfaces.OrderProcessor;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthorizedOrderProcessor  implements OrderProcessor {

    private final NotificationService notificationService;
    private final SimpleOrderProcessor simpleOrderProcessor;
    private final AuthorizedProcessorNotificationBuilder authorizedProcessorNotificationBuilder;
    private final NotificationService userNotificationService;
    private Optional<UserDto> userDto;
    public AuthorizedOrderProcessor(

            @Qualifier("emailNotificationService") NotificationService notificationService,
            @Qualifier("userNotificationService") NotificationService userNotificationService,
            @Qualifier("simpleOrderProcessor") OrderProcessor simpleOrderProcessor,
            AuthorizedProcessorNotificationBuilder authorizedProcessorNotificationBuilder
    ) {
        this.notificationService = notificationService;
        this.simpleOrderProcessor = (SimpleOrderProcessor) simpleOrderProcessor;
        this.authorizedProcessorNotificationBuilder = authorizedProcessorNotificationBuilder;
        this.userNotificationService = userNotificationService;
    }

    @Override
    public Order processOrder(PerformOrderRequest performOrderRequest, Optional<UserDto> user) {

        Order order = simpleOrderProcessor.processOrder(performOrderRequest, user);
        SimpleOrderProcessor.BonusWriteOff refreshedBonuses = simpleOrderProcessor.buildBonusWriteOff(performOrderRequest);

        EmailNotification emailNotification = authorizedProcessorNotificationBuilder.buildOrderNotification(order);
        UserNotification userNotification = authorizedProcessorNotificationBuilder.buildUserNotification(order, refreshedBonuses.getBonuses());

        notificationService.send(emailNotification);
        userNotificationService.send(userNotification);

        return order;
    }

    public void setUserCredentials(Optional<UserDto> user){
        this.userDto = user;
    }


}
