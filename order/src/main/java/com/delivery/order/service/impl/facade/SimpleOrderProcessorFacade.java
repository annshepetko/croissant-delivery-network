package com.delivery.order.service.impl.facade;

import com.delivery.order.dto.OrderBody;
import com.delivery.order.service.interfaces.OrderProcessor;
import com.delivery.order.service.interfaces.facade.OrderProcessorFacade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleOrderProcessorFacade implements OrderProcessorFacade {

    private final OrderProcessor simpleProcessor;

    @Override
    public void handleOrder(OrderBody orderBody) {
        simpleProcessor.processOrder(orderBody);
    }
}
