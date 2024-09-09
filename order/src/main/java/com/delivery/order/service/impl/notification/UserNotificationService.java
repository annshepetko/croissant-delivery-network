package com.delivery.order.service.impl.notification;

import com.delivery.order.kafka.notification.UserNotification;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserNotificationService implements NotificationService<UserNotification> {


    @Qualifier("userKafkaTemplate")
    private final KafkaTemplate<String, UserNotification> kafkaTemplate;


    public UserNotificationService(KafkaTemplate<String, UserNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(UserNotification notification) {
        kafkaTemplate.send("user-order-confirmation", "message", notification);
    }
}
