package com.delivery.order.service.price.interfaces;

import com.delivery.order.dto.Bonuses;

import java.math.BigDecimal;

public interface DiscountService {

    BigDecimal calculateTotalPrice(BigDecimal generalPrice, Bonuses bonuses);
}
