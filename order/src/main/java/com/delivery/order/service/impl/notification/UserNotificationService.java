package com.delivery.order.service.impl.notification;

import com.delivery.order.kafka.notification.UserNotification;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserNotificationService implements NotificationService<UserNotification> {

    @Qualifier("userKafkaTemplate")
    private final KafkaTemplate<String, UserNotification> kafkaTemplate;

    public UserNotificationService(KafkaTemplate<String, UserNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(UserNotification notification) {

        log.info("Preparing to send user notification for user: {}", notification.getEmail());

        try {
            kafkaTemplate.send("user-order-confirmation", "message", notification);

            log.info("Successfully sent user notification for user: {}", notification.getEmail());
        } catch (Exception e) {

            log.error("Failed to send user notification for user: {}. Error: {}", notification.getEmail(), e.getMessage(), e);

            throw new RuntimeException("Failed to send user notification", e);
        }
    }
}
