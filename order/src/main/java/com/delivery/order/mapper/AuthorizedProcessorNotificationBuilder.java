package com.delivery.order.mapper;

import com.delivery.order.entity.Order;
import com.delivery.order.kafka.notification.CommonNotification;
import com.delivery.order.kafka.notification.EmailNotification;
import com.delivery.order.kafka.notification.UserNotification;
import com.delivery.order.service.impl.notification.EmailNotificationService;
import com.delivery.order.service.impl.notification.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AuthorizedProcessorNotificationBuilder {

    private final OrderMapper orderMapper;
    private final EmailNotificationService emailNotificationService;
    private final UserNotificationService userNotificationService;

    public EmailNotification buildOrderNotification(Order order){

        return EmailNotification.builder()
                .orderId(order.getId())
                .email(order.getEmail())
                .notificationService(emailNotificationService)
                .products(orderMapper.mapToOrderedProductRequests(order.getOrderedProducts()))
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public List<CommonNotification> createNotifications(Order order, Double bonuses ){
        return List.of(buildOrderNotification(order), buildUserNotification(order, bonuses));
    }

    public UserNotification buildUserNotification(Order order, Double bonuses){

        return UserNotification.builder()
                .orderId(order.getId())
                .notificationService(userNotificationService)
                .bonuses(bonuses)
                .email(order.getEmail())
                .build();
    }


}
