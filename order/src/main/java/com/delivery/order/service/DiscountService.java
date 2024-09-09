package com.delivery.order.service;

import com.delivery.order.dto.OrderProductRequest;
import com.delivery.order.service.impl.order.SimpleOrderProcessor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DiscountService {

    public BigDecimal calculateTotalPrice(List<OrderProductRequest> order, SimpleOrderProcessor.BonusWriteOff bonusWriteOff) {

        BigDecimal generalPrice = order.stream()
                .map(product -> product.price().multiply(BigDecimal.valueOf(product.amount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return calculateDiscount(bonusWriteOff, generalPrice);
    }

    private BigDecimal calculateDiscount(SimpleOrderProcessor.BonusWriteOff bonuses, BigDecimal price) {
        return calculateGeneralPriceBasedOnUserChoice(bonuses, price);
    }

    private BigDecimal calculateGeneralPriceBasedOnUserChoice(
            SimpleOrderProcessor.BonusWriteOff bonusWriteOff,
            BigDecimal price
    ){
        if (bonusWriteOff.getIsWriteOff()){
            double bonuses = bonusWriteOff.getBonuses();
            bonusWriteOff.setBonuses(0.0);

            return price.subtract(BigDecimal.valueOf(bonuses));

        }
        return price;
    }

}
