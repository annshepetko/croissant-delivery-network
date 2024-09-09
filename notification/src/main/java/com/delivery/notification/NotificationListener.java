package com.delivery.notification;

import com.delivery.notification.kafka.consumer.dto.EmailNotification;
import com.delivery.notification.services.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationListener {

    private final MailService mailService;

    @KafkaListener(topics = "order-confirmation", groupId = "notification-id")
    public void listenEmailNotification(EmailNotification message) {

        try {
            mailService.sendEmail(message);
        } catch (MessagingException e) {

            log.warn("EXCEPTION WHILE SENDING EMAIL ::" + e.getCause().getMessage());
        }
    }

}
