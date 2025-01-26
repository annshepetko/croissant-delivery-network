package com.delivery.order.service;

import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.dto.Bonuses;
import com.delivery.order.service.price.AuthDiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthDiscountServiceTest {


    @InjectMocks
    private AuthDiscountService authDiscountService;

    @BeforeEach
    void setUp() {
        authDiscountService = new AuthDiscountService();
    }

    @Test
    void calculateTotalPrice() {

       Bonuses bonuses = new Bonuses(30.0, false);

        List<OrderProductDto> products = new ArrayList<>();
        products.add(OrderProductDto.builder()
                        .amount(1)
                        .price(BigDecimal.valueOf(30.0))
                .build());

        products.add(OrderProductDto.builder()
                .price(BigDecimal.valueOf(30.0))
                        .amount(1)
                .build());

       BigDecimal totalPrice = authDiscountService.calculateTotalPrice(BigDecimal.valueOf(60), bonuses);


       assertEquals(BigDecimal.valueOf(60.0), totalPrice);

    }
}