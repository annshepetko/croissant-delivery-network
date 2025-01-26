package com.delivery.order.service.impl.order;

import com.delivery.order.entity.Order;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.service.entity.OrderEntityService;
import com.delivery.order.service.interfaces.OrderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SimpleOrderProcessor implements OrderProcessor {


    private static final Logger logger = LoggerFactory.getLogger(SimpleOrderProcessor.class);

    private final OrderEntityService orderEntityService;
    private final OrderMapper orderMapper;

    public SimpleOrderProcessor(
            OrderEntityService orderEntityService,
            OrderMapper orderMapper
    ) {
        this.orderEntityService = orderEntityService;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order processOrder(OrderBody order) {

        logger.info("Staring to process the order");

        Order preparedOrder = prepareOrder(order);

        return orderEntityService.saveOrder(preparedOrder);
    }

    private Order prepareOrder(OrderBody order) {
        return orderMapper.buildOrder(order);
    }


}
