package com.delivery.order.service.impl.notification;


import com.delivery.order.kafka.notification.EmailNotification;
import com.delivery.order.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService<EmailNotification> {

    @Qualifier("emailKafkaTemplate")
    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;

    public EmailNotificationService(KafkaTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public  void send(EmailNotification notification) {
        kafkaTemplate.send("order-confirmation", "message", notification);
    }
}
