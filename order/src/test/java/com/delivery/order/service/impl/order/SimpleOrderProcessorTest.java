package com.delivery.order.service.impl.order;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.DiscountService;
import com.delivery.order.service.OrderEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SimpleOrderProcessorTest {

    @Mock
    private DiscountService discountService;

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

        SimpleOrderProcessor.BonusWriteOff bonusWriteOff = new SimpleOrderProcessor.BonusWriteOff(10.0, true);

        OrderRequest orderRequest = createPerformOrderRequest(10.0, true);

        OrderMapper.OrderBody orderBody = new OrderMapper.OrderBody(
                orderRequest,
                "user@example.com",
                BigDecimal.valueOf(20.0)
        );

        Order order = Order.builder()
                .totalPrice(BigDecimal.valueOf(10.0))
                .build();

        UserDto userDto = new UserDto("user@example.com", 10.0);
        Optional<UserDto> userOptional = Optional.of(userDto);

        BigDecimal expectedPrice = BigDecimal.valueOf(10.0);

        when(discountService.calculateTotalPrice(orderRequest.orderProductDtos(), bonusWriteOff)).thenReturn(expectedPrice);
        when(orderEntityService.saveOrder(any(Order.class))).thenReturn(order);

        Order resultOrder = simpleOrderProcessor.processOrder(orderRequest, userOptional);

        assertEquals(expectedPrice, resultOrder.getTotalPrice()); // Assuming you have a getPrice method in Order
        verify(discountService, times(1)).calculateTotalPrice(any(), any());
        verify(orderEntityService, times(1)).saveOrder(any());
    }

    @Test
    void testProcessOrderWithoutWriteOffBonuses() {


        OrderRequest orderRequest = createPerformOrderRequest(10.0, false);

        OrderMapper.OrderBody orderBody = new OrderMapper.OrderBody(
                orderRequest,
                "user@example.com",
                BigDecimal.valueOf(20.0)
        );

        Order order = Order.builder()
                .totalPrice(BigDecimal.valueOf(20.0))
                .build();

        SimpleOrderProcessor.BonusWriteOff bonusWriteOff = new SimpleOrderProcessor.BonusWriteOff(10.0, false);
        UserDto userDto = new UserDto("user@example.com", 10.0);
        Optional<UserDto> userOptional = Optional.of(userDto);
        BigDecimal expectedPrice = BigDecimal.valueOf(20.0); // No bonuses used

        when(discountService.calculateTotalPrice(orderRequest.orderProductDtos(), bonusWriteOff)).thenReturn(expectedPrice);
        when(orderEntityService.saveOrder(any(Order.class))).thenReturn(order);

        Order resultOrder = simpleOrderProcessor.processOrder(orderRequest, userOptional);

        assertEquals(expectedPrice, resultOrder.getTotalPrice());
        verify(discountService, times(1)).calculateTotalPrice(any(), any());
        verify(orderEntityService, times(1)).saveOrder(any(Order.class));
    }


    @Test
    void testBuildBonusWriteOffWithWriteOff() {
        // Arrange
        OrderRequest orderRequest = createPerformOrderRequest(10.0, true);

        // Act
        SimpleOrderProcessor.BonusWriteOff bonusWriteOff = simpleOrderProcessor.buildBonusWriteOff(orderRequest);

        // Assert
        assertEquals(10.0, bonusWriteOff.getBonuses());
        assertEquals(true, bonusWriteOff.getIsWriteOff());
    }

    @Test
    void testBuildBonusWriteOffWithoutWriteOff() {
        // Arrange
        OrderRequest orderRequest = createPerformOrderRequest(10.0, false);

        // Act
        SimpleOrderProcessor.BonusWriteOff bonusWriteOff = simpleOrderProcessor.buildBonusWriteOff(orderRequest);

        // Assert
        assertEquals(12.0, bonusWriteOff.getBonuses()); // +2 added
        assertEquals(false, bonusWriteOff.getIsWriteOff());
    }

    private OrderRequest createPerformOrderRequest(Double bonuses, Boolean isWriteOff) {
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
                bonuses,
                isWriteOff
        );
    }
}
