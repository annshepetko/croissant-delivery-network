package com.delivery.order.service.impl.order;

import com.delivery.order.dto.OrderRequest;
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
    private final OrderMapper orderMapper;

    public SimpleOrderProcessor(
            DiscountService discountService,
            OrderEntityService orderEntityService,
            OrderMapper orderMapper
    ) {
        this.discountService = discountService;
        this.orderEntityService = orderEntityService;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order processOrder(OrderRequest orderRequest, Optional<UserDto> user) {

        logger.info("Staring to process the order");

        BigDecimal price = performPriceCalculation(orderRequest);
        Order order = prepareOrder(orderRequest, user, price);

        return orderEntityService.saveOrder(order);
    }

    private BigDecimal performPriceCalculation(OrderRequest orderRequest) {
        BonusWriteOff bonuses = buildBonusWriteOff(orderRequest);
        return calculatePrice(orderRequest, bonuses);
    }

    private Order prepareOrder(
            OrderRequest orderRequest,
            Optional<UserDto> user,
            BigDecimal price
    ) {
        String email = handleUserResponse(user);
        return orderMapper.buildOrder(orderMapper.buildOrderBody(orderRequest,email, price));
    }

    private  String handleUserResponse(Optional<UserDto> user) {

        return user.map(UserDto::email).orElse("empty");
    }

    public BonusWriteOff buildBonusWriteOff(OrderRequest orderRequest) {

        return createBonusWriteOff(orderRequest);
    }

    private BigDecimal calculatePrice(OrderRequest orderRequest, BonusWriteOff bonuses) {
        return discountService.calculateTotalPrice(orderRequest.orderProductDtos(), bonuses);
    }

    private BonusWriteOff createBonusWriteOff(OrderRequest orderRequest) {
        BonusWriteOff bonusWriteOff = new BonusWriteOff(
                orderRequest.userBonuses(),
                orderRequest.isWriteOffBonuses()
        );
        if (!bonusWriteOff.getIsWriteOff()){
            updateBonuses(bonusWriteOff);
        }

        return bonusWriteOff;
    }

    private void updateBonuses(BonusWriteOff bonusWriteOff) {
        bonusWriteOff.setBonuses(bonusWriteOff.getBonuses() + 2);
        logger.info("Refreshing user bonuses");
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
