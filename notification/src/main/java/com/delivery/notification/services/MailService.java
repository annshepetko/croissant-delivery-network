package com.delivery.notification.services;


import com.delivery.notification.kafka.consumer.dto.EmailNotification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class MailService {



    @Value("${spring.mail.username}")
    private String fromEmail;

    private final TemplateEngine templateEngine;
    private final JavaMailSenderImpl javaMailSender;


    @Async
    public void sendEmail(EmailNotification message) throws MessagingException {

        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = buildMimeMessageHelper(message, mimeMailMessage);

        javaMailSender.send(mimeMailMessage);
    }

    private MimeMessageHelper buildMimeMessageHelper(EmailNotification message, MimeMessage mimeMessage) throws MessagingException {

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        Context context = buildContext(message);
        String processedHtml = templateEngine.process("email-message", context);
        mimeMessageHelper.setTo(message.email());
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setText(processedHtml, true);
        mimeMessageHelper.setSubject("order-confirmation");

        return mimeMessageHelper;
    }

    private static Context buildContext(EmailNotification message) {
        Context context = new Context();

        context.setVariable("email", message.email());
        context.setVariable("products", message.products());
        context.setVariable("orderId", message.orderId());
        context.setVariable("totalPrice", message.totalPrice());
        return context;
    }


}
