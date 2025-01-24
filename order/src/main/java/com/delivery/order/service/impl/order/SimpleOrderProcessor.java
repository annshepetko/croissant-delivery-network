package com.delivery.order.service.impl.order;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.DiscountService;
import com.delivery.order.service.OrderEntityService;
import com.delivery.order.service.interfaces.OrderProcessor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SimpleOrderProcessor  implements OrderProcessor {


    private static final Logger logger = LoggerFactory.getLogger(SimpleOrderProcessor.class);

    private final DiscountService discountService;
    private final OrderEntityService orderEntityService;

    public SimpleOrderProcessor(DiscountService discountService, OrderEntityService orderEntityService) {
        this.orderEntityService = orderEntityService;
        this.discountService = discountService
    }

    @Override
    public Order processOrder(PerformOrderRequest performOrderRequest) {

        logger.info("Staring to process the order");
        BigDecimal price =
        Order order =

        return orderEntityService.saveOrder();
    }

}
