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

    public BigDecimal calculateTotalPrice(List<OrderProductDto> order, SimpleOrderProcessor.BonusWriteOff bonusWriteOff) {

        logger.info("Received order with {} products", order.size());

        BigDecimal generalPrice = order.stream()
                .map(product -> {
                    BigDecimal productTotal = product.price().multiply(BigDecimal.valueOf(product.amount()));
                    logger.debug("Product: {}, Price per unit: {}, Quantity: {}, Total: {}",
                            product.name(), product.price(), product.amount(), productTotal);
                    return productTotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        logger.info("Total price for all products before discount: {}", generalPrice);

        return calculateDiscount(bonusWriteOff, generalPrice);
    }

    private BigDecimal calculateDiscount(SimpleOrderProcessor.BonusWriteOff bonuses, BigDecimal price) {
        logger.info("Calculating discount based on bonuses");
        return calculateGeneralPriceBasedOnUserChoice(bonuses, price);
    }

    private BigDecimal calculateGeneralPriceBasedOnUserChoice(
            SimpleOrderProcessor.BonusWriteOff bonusWriteOff,
            BigDecimal price
    ) {
        if (bonusWriteOff.getIsWriteOff()) {
            double bonuses = bonusWriteOff.getBonuses();
            logger.info("Applying {} bonuses to reduce the price. Original price: {}", bonuses, price);

            bonusWriteOff.setBonuses(0.0);

            BigDecimal finalPrice = price.subtract(BigDecimal.valueOf(bonuses));
            logger.info("Final price after applying bonuses: {}", finalPrice);
            return finalPrice;
        }

        logger.info("No bonuses applied. Final price remains: {}", price);
        return price;
    }
}
