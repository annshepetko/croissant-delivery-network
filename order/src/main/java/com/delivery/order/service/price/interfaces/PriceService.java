package com.delivery.order.service.price.interfaces;

import com.delivery.order.dto.Bonuses;
import com.delivery.order.dto.OrderBody;

import java.math.BigDecimal;

public interface PriceService {

    BigDecimal calculatePrice(OrderBody orderBody, Bonuses bonuses);
}
