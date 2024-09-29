package com.delivery.notification.services.impl;

import com.delivery.notification.dto.mail.OrderEmailNotification;
import com.delivery.notification.services.interfaces.NotificationService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class OrderConfirmationEmailService extends NotificationService<OrderEmailNotification> {

    private static final Logger logger = LoggerFactory.getLogger(OrderConfirmationEmailService.class);

    public OrderConfirmationEmailService(
            TemplateEngine templateEngine,
            JavaMailSenderImpl javaMailSender
    ) {
        super(templateEngine, javaMailSender);
    }

    @Override
    protected String getTemplate() {
        return "email-message";
    }

    @Override
    public void sendEmail(OrderEmailNotification message) throws MessagingException {
        logger.info("Sending order confirmation email to: {}", message.getEmail());
        super.sendEmail(message);
        logger.info("Order confirmation email sent successfully to: {}", message.getEmail());
    }

    @Override
    protected void setContextVariables(Context context, OrderEmailNotification orderEmailNotification) {
        context.setVariable("email", orderEmailNotification.getEmail());
        context.setVariable("products", orderEmailNotification.getProducts());
        context.setVariable("orderId", orderEmailNotification.getOrderId());
        context.setVariable("totalPrice", orderEmailNotification.getTotalPrice());
    }

    @Override
    protected String getSubject() {
        return "order-confirmation";
    }
}
