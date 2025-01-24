package com.delivery.order.service.interfaces;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.openFeign.dto.UserDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderProcessor {


    Order processOrder(PerformOrderRequest performOrderRequest);
}
