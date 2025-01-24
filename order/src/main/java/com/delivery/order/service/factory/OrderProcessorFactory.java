package com.delivery.order.service.factory;

import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.OrderProcessor;

import java.util.Optional;

public interface OrderProcessorFactory {
    OrderProcessor getOrderProcessor(Optional<UserDto> userDto);
}
