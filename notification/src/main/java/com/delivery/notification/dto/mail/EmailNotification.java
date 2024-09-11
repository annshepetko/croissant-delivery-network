package com.delivery.notification.dto.mail;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class EmailNotification {
    private String email;

    public EmailNotification(String email) {
        this.email = email;
    }

    public EmailNotification() {
    }
}
