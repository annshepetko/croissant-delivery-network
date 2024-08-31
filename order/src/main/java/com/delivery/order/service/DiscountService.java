package com.delivery.order.service;

import com.delivery.order.dto.OrderProductRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiscountService {

    public BigDecimal calculateTotalPrice(List<OrderProductRequest> order, Double discountPercent) {

        BigDecimal generalPrice = order.stream()
                .map(OrderProductRequest::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return calculateDiscount(discountPercent, generalPrice);
    }

    private BigDecimal calculateDiscount(Double percents, BigDecimal price) {
        BigDecimal discount = price.multiply(BigDecimal.valueOf(percents)).divide(BigDecimal.valueOf(100));
        return price.subtract(discount);
    }



}
