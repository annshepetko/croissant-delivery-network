package com.delivery.notification.kafka.consumer.dto;

import com.delivery.notification.dto.mail.EmailNotification;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@SuperBuilder
@Getter
@Setter
public class UserResetPasswordNotification extends EmailNotification {

    private String token;

    public UserResetPasswordNotification() {
        super();
    }
}
