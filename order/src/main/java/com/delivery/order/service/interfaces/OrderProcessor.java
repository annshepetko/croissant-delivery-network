package com.delivery.order.service.interfaces;

import com.delivery.order.entity.Order;
import com.delivery.order.dto.OrderBody;

public interface OrderProcessor {
    Order processOrder(OrderBody orderRequest);
}
