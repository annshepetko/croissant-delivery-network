package com.delivery.notification.services.interfaces;

import com.delivery.notification.dto.mail.EmailNotification;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RequiredArgsConstructor
public abstract class NotificationService <T extends EmailNotification> {


    @Value("${spring.mail.username}")
    private String fromEmail;

    protected final TemplateEngine templateEngine;

    private final JavaMailSenderImpl javaMailSender;

    protected abstract String getTemplate();
    protected abstract void setContextVariables(Context context, T t);

    protected abstract String getSubject();



    public void sendEmail(T message) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        Context context = new Context();

        setContextVariables(context, message);

        String processedHtml = templateEngine.process(getTemplate(), context);

        mimeMessageHelper.setTo(message.getEmail());
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setText(processedHtml, true);
        mimeMessageHelper.setSubject(getSubject());

        javaMailSender.send(mimeMessage);


    }


}
