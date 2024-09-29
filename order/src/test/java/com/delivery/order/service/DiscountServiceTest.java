package com.delivery.order.service;

import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.service.impl.order.SimpleOrderProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {



    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService();
    }

    @Test
    void calculateTotalPrice() {

        SimpleOrderProcessor.BonusWriteOff bonusWriteOff = new SimpleOrderProcessor.BonusWriteOff(30.0, false);

        List<OrderProductDto> products = new ArrayList<>();
        products.add(OrderProductDto.builder()
                        .amount(1)
                        .price(BigDecimal.valueOf(30.0))
                .build());

        products.add(OrderProductDto.builder()
                .price(BigDecimal.valueOf(30.0))
                        .amount(1)
                .build());

       BigDecimal totalPrice = discountService.calculateTotalPrice(products, bonusWriteOff);


       assertEquals(BigDecimal.valueOf(60.0), totalPrice);

    }
}