package com.delivery.order.service.interfaces;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.openFeign.dto.UserDto;

import java.util.Optional;

public interface OrderProcessor {
    Order processOrder(OrderRequest orderRequest, Optional<UserDto> user);
}
