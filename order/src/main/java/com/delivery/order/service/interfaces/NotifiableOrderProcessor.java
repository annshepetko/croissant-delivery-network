package com.delivery.order.service.interfaces;

import com.delivery.order.kafka.notification.CommonNotification;

import java.util.List;

public interface NotifiableOrderProcessor extends OrderProcessor {
    List<CommonNotification> getNotifications();
}
