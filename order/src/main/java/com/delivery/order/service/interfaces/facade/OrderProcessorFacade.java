package com.delivery.order.service.interfaces.facade;

import com.delivery.order.dto.OrderBody;

public interface OrderProcessorFacade {
    void handleOrder(OrderBody orderBody);
}
