package com.delivery.order.service.factory;

import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.interfaces.OrderProcessor;
import com.delivery.order.service.interfaces.facade.OrderProcessorFacade;

import java.util.Optional;

public interface OrderProcessorFacadeFactory {
    OrderProcessorFacade getOrderProcessor(Optional<UserDto> userDto);
}
