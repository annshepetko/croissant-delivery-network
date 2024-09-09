package com.delivery.order.service.interfaces;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.openFeign.dto.UserDto;

import java.util.Optional;

public interface OrderProcessor {
    Order processOrder(PerformOrderRequest performOrderRequest, Optional<UserDto> user);
}
