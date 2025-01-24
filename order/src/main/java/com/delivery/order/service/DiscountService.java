package com.delivery.order.service;

import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.service.impl.order.SimpleOrderProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiscountService {

    private static final Logger logger = LoggerFactory.getLogger(DiscountService.class);

    public BigDecimal calculateTotalPrice(List<OrderProductDto> order, double discount) {

        logger.info("Received order with {} products", order.size());

        BigDecimal generalPrice = order.stream()
                .map(product -> product.price().multiply(BigDecimal.valueOf(product.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        logger.info("Total price for all products before discount: {}", generalPrice);

        return generalPrice.subtract(BigDecimal.valueOf(discount));
    }


}
