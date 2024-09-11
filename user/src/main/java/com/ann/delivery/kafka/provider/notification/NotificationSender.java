package com.ann.delivery.kafka.provider.notification;


public interface NotificationSender <T> {

    void send(T t);

}
