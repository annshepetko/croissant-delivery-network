package com.delivery.order.service.interfaces;


import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.openFeign.dto.UserDto;

import java.util.Optional;

public abstract class OrderHandler {

    protected OrderHandler next;

    public OrderHandler(OrderHandler next) {
        this.next = next;
    }

    public void setNext(OrderHandler orderHandler){
        this.next = orderHandler;
    }

    public void handle(PerformOrderRequest performOrderRequest, Optional<UserDto> userDto){
        if (this.next != null){
            next.handle(performOrderRequest, userDto);
        }
    }
}
