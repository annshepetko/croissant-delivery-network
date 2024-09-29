package com.delivery.order.service.impl.order;

import com.delivery.order.dto.PerformOrderRequest;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.DiscountService;
import com.delivery.order.service.OrderEntityService;
import com.delivery.order.service.interfaces.OrderProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SimpleOrderProcessor implements OrderProcessor {

    private static final Logger logger = LoggerFactory.getLogger(SimpleOrderProcessor.class);

    private final DiscountService discountService;
    private final OrderEntityService orderEntityService;

    @Override
    public Order processOrder(PerformOrderRequest performOrderRequest, Optional<UserDto> user) {

        logger.info("Staring to process the order");

        BonusWriteOff bonuses = buildBonusWriteOff(performOrderRequest);

        String email = user.map(UserDto::email).orElse("empty");

        BigDecimal discountedOrderPrice = discountService.calculateTotalPrice(performOrderRequest.orderProductDtos(), bonuses);
        OrderMapper.OrderBody orderBody = new OrderMapper.OrderBody(performOrderRequest, email, discountedOrderPrice);

        return orderEntityService.saveOrder(orderBody);
    }

    public BonusWriteOff buildBonusWriteOff(PerformOrderRequest performOrderRequest) {


        BonusWriteOff bonusWriteOff = new BonusWriteOff(
                performOrderRequest.userBonuses(),
                performOrderRequest.isWriteOffBonuses()
        );
        if (!bonusWriteOff.getIsWriteOff()){

            bonusWriteOff.setBonuses(bonusWriteOff.getBonuses() + 2);
            logger.info("Refreshing user bonuses");
        }
        return bonusWriteOff;
    }

    @Setter
    @Getter
    public static class BonusWriteOff {

        public BonusWriteOff(Double bonuses, Boolean isWriteOff) {
            this.bonuses = bonuses;
            this.isWriteOff = isWriteOff;
        }

        private Double bonuses;
        private Boolean isWriteOff;
    }
}
