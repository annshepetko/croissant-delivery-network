package com.delivery.order.service.interfaces;

public interface NotificationService<T> {
     void send(T notification);
}
