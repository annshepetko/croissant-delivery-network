package com.ann.delivery.kafka.provider.notification.impl;

import com.ann.delivery.kafka.provider.notification.NotificationSender;
import com.ann.delivery.kafka.provider.notification.dto.UserForgotPasswordNotification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service

public class ForgotPasswordSender implements NotificationSender<UserForgotPasswordNotification> {

    private final KafkaTemplate<String, UserForgotPasswordNotification> kafkaTemplate;

    public ForgotPasswordSender(
            KafkaTemplate<String, UserForgotPasswordNotification> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(UserForgotPasswordNotification notification) {
        kafkaTemplate.send("forgot-password","token", notification);
    }
}
