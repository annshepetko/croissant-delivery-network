package com.delivery.notification.services.impl;

import com.delivery.notification.kafka.consumer.dto.UserResetPasswordNotification;
import com.delivery.notification.services.interfaces.NotificationService;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class UserForgotPasswordEmailService extends NotificationService<UserResetPasswordNotification> {


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
        super.sendEmail(message);
    }

    @Override
    protected void setContextVariables(Context context, UserResetPasswordNotification notification) {

        context.setVariable("token", notification.getToken());

    }

    @Override
    protected String getSubject() {
        return  "reset-password";
    }

}
