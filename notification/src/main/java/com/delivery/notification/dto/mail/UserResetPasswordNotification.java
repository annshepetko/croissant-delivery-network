package com.delivery.notification.dto.mail;

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
