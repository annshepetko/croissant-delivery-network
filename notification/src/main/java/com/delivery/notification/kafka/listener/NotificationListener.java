package com.delivery.notification.kafka.listener;

import com.delivery.notification.dto.mail.OrderEmailNotification;
import com.delivery.notification.dto.mail.UserResetPasswordNotification;
import com.delivery.notification.kafka.consumer.dto.OrderConfirmationKafkaMessage;
import com.delivery.notification.services.impl.OrderConfirmationEmailService;
import com.delivery.notification.services.impl.UserForgotPasswordEmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationListener {

    private final UserForgotPasswordEmailService userForgotPasswordNotificationService;
    private final OrderConfirmationEmailService orderConfirmationEmailService;

    @KafkaListener(topics = "order-confirmation", groupId = "notification-id", containerFactory = "kafkaOrderConfirmationContainerFactoryContainerFactory")
    public void listenEmailNotification(OrderConfirmationKafkaMessage message) {

        try {
            orderConfirmationEmailService.sendEmail(new OrderEmailNotification(message));
        } catch (MessagingException e) {

            log.warn("EXCEPTION WHILE SENDING EMAIL ::" + e.getCause().getMessage());
        }
    }

    @KafkaListener(topics = "forgot-password", groupId = "user-forgot-id", containerFactory = "kafkaListenerContainerFactory")
    public void listenUserForgotPasswordNotification(UserResetPasswordNotification token) {

        log.info("ENCRYPTED-TOKEN:: " + token.getToken());
        try {

            userForgotPasswordNotificationService.sendEmail(token);
        } catch (MessagingException e) {

            log.warn("EXCEPTION WHILE SENDING EMAIL ::" + e.getCause().getMessage());
        }
    }

}
