package com.delivery.order.kafka.notification;

import com.delivery.order.service.interfaces.NotificationService;

public interface CommonNotification {
    void send(NotificationService notificationService);
    NotificationService getNotificationService();

}
