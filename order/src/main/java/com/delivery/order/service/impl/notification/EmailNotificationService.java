package com.delivery.order.service.impl.notification;


import com.delivery.order.kafka.notification.EmailNotification;
import com.delivery.order.service.interfaces.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailNotificationService implements NotificationService<EmailNotification> {

    @Qualifier("emailKafkaTemplate")
    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;

    public EmailNotificationService(KafkaTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(EmailNotification notification) {
        log.info("Preparing to send email notification for email: {}", notification.email());

        try {

            kafkaTemplate.send("order-confirmation", "message", notification);

            log.info("Successfully sent email notification for email: {}", notification.email());
        } catch (Exception e) {

            log.error("Failed to send email notification for email: {}. Error: {}", notification.email(), e.getMessage(), e);
            throw new RuntimeException("Failed to send email notification", e);
        }
    }
}
