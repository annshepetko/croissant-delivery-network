package com.delivery.order.service.impl.order;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.entity.Order;
import com.delivery.order.dto.Bonuses;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.service.price.AuthDiscountService;
import com.delivery.order.service.entity.OrderEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SimpleOrderProcessorTest {

    @Mock
    private AuthDiscountService authDiscountService;

    @Mock
    private OrderEntityService orderEntityService;

    @InjectMocks
    private SimpleOrderProcessor simpleOrderProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessOrderWithWriteOffBonuses() {

        Bonuses bonuses = new Bonuses(10.0, true);

        OrderRequest orderRequest = createPerformOrderRequest();

        OrderBody orderBody = createOrderBodyTest();

        Order order = Order.builder()
                .totalPrice(BigDecimal.valueOf(10.0))
                .build();

        BigDecimal expectedPrice = BigDecimal.valueOf(10.0);

        when(authDiscountService.calculateTotalPrice(BigDecimal.valueOf(100), bonuses)).thenReturn(expectedPrice);
        when(orderEntityService.saveOrder(any(Order.class))).thenReturn(order);

        Order resultOrder = simpleOrderProcessor.processOrder(orderBody);

        assertEquals(expectedPrice, resultOrder.getTotalPrice()); // Assuming you have a getPrice method in Order
        verify(authDiscountService, times(1)).calculateTotalPrice(any(), any());
        verify(orderEntityService, times(1)).saveOrder(any());
    }

    @Test
    void testProcessOrderWithoutWriteOffBonuses() {

        OrderBody orderBody = createOrderBodyTest();

        Order order = Order.builder()
                .totalPrice(BigDecimal.valueOf(20.0))
                .build();

        Bonuses bonuses = new Bonuses(10.0, false);

        BigDecimal expectedPrice = BigDecimal.valueOf(20.0); // No bonuses used

        when(authDiscountService.calculateTotalPrice(BigDecimal.valueOf(12), bonuses)).thenReturn(expectedPrice);
        when(orderEntityService.saveOrder(any(Order.class))).thenReturn(order);

        Order resultOrder = simpleOrderProcessor.processOrder(orderBody);

        assertEquals(expectedPrice, resultOrder.getTotalPrice());
        verify(authDiscountService, times(1)).calculateTotalPrice(any(), any());
        verify(orderEntityService, times(1)).saveOrder(any(Order.class));
    }


    @Test
    void testBuildBonusWriteOffWithWriteOff() {
        // Arrange
        OrderRequest orderRequest = createPerformOrderRequest();

        // Act
        Bonuses bonuses = Bonuses.buildBonuses(createOrderBodyTest());

        // Assert
        assertEquals(10.0, bonuses.getBonuses());
        assertEquals(true, bonuses.getIsWriteOff());
    }

    @Test
    void testBuildBonusWriteOffWithoutWriteOff() {
        // Arrange
        OrderRequest orderRequest = createPerformOrderRequest();

        // Act
        Bonuses bonuses = Bonuses.buildBonuses(createOrderBodyTest());

        // Assert
        assertEquals(12.0, bonuses.getBonuses()); // +2 added
        assertEquals(false, bonuses.getIsWriteOff());
    }

    private OrderRequest createPerformOrderRequest() {
        return new OrderRequest(
                List.of(OrderProductDto.builder()
                                .amount(1)
                                .price(BigDecimal.valueOf(10))
                                .build(),
                        OrderProductDto.builder()
                                .amount(1)
                                .price(BigDecimal.valueOf(10))
                                .build()), // Mock product DTOs
                "John",
                "Doe",
                "+380670123456",
                null,
                2.0,
                true
        );
    }

    private OrderBody createOrderBodyTest(){
        return new OrderBody(createPerformOrderRequest(), BigDecimal.valueOf(100), "ann.sh@gmail.com");
    }
}
