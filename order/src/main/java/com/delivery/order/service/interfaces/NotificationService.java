package com.delivery.order.service.interfaces;

import com.delivery.order.kafka.notification.CommonNotification;

public interface NotificationService<T extends CommonNotification> {
     void send(T notification);
}