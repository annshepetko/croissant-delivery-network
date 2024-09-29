package com.delivery.notification.services.impl;

import com.delivery.notification.dto.mail.UserResetPasswordNotification;
import com.delivery.notification.services.interfaces.NotificationService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class UserForgotPasswordEmailService extends NotificationService<UserResetPasswordNotification> {

    private static final Logger logger = LoggerFactory.getLogger(UserForgotPasswordEmailService.class);

    public UserForgotPasswordEmailService(
            TemplateEngine templateEngine,
            JavaMailSenderImpl javaMailSender
    ) {
        super(templateEngine, javaMailSender);
    }

    @Override
    protected String getTemplate() {
        return "user-password";
    }

    @Override
    public void sendEmail(UserResetPasswordNotification message) throws MessagingException {

        logger.info("Preparing to send password reset email to: {}", message.getEmail());

        super.sendEmail(message);

        logger.info("Password reset email sent successfully to: {}", message.getEmail());
    }

    @Override
    protected void setContextVariables(Context context, UserResetPasswordNotification notification) {

        logger.debug("Setting context variable for token: {}", notification.getToken());

        context.setVariable("token", notification.getToken());
    }

    @Override
    protected String getSubject() {
        return "reset-password";
    }
}
