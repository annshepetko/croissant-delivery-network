package com.ann.delivery.kafka.provider.notification.dto;


import lombok.Builder;

@Builder
public record UserForgotPasswordNotification(
        String token,
        String email
){
}
