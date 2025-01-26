package com.delivery.order.service;

import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.repo.OrderRepository;
import com.delivery.order.dto.user.OrderPageUserDto;
import com.delivery.order.dto.user.OrderUserServiceDto;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.service.api.user.OrderUserService;
import com.delivery.order.service.entity.OrderEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUserServiceTest {

    @Mock
    private OrderEntityService orderEntityService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderUserService orderUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUserOrders() {
        String email = "user@example.com";
        Pageable pageable = PageRequest.of(0, 10);

        // Mocking a page of orders
        List<OrderUserServiceDto> orderList = List.of(
                OrderUserServiceDto.builder()
                        .orderedAt(LocalDateTime.now())
                        .id(1L)
                        .totalPrice(BigDecimal.valueOf(100))
                        .build()
        );
        Page<OrderUserServiceDto> page = new PageImpl<>(orderList);

        when(orderRepository.findAllByEmailAndMap(email, pageable)).thenReturn(Optional.of(page));

        Optional<Page<OrderUserServiceDto>> result = orderUserService.getAllUserOrders(email, pageable);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getTotalElements());
        assertEquals(1L, result.get().getContent().get(0).id());
        assertEquals(BigDecimal.valueOf(100), result.get().getContent().get(0).totalPrice());

        verify(orderRepository, times(1)).findAllByEmailAndMap(email, pageable);
    }

    @Test
    void testGetAllUserOrders_Empty() {
        String email = "user@example.com";
        Pageable pageable = PageRequest.of(0, 10);

        when(orderRepository.findAllByEmailAndMap(email, pageable)).thenReturn(Optional.empty());

        Optional<Page<OrderUserServiceDto>> result = orderUserService.getAllUserOrders(email, pageable);

        assertTrue(result.isEmpty());

        verify(orderRepository, times(1)).findAllByEmailAndMap(email, pageable);
    }

    @Test
    void testGetOrderPageForUser() {
        Long orderId = 1L;
        Order order = Order.builder()
                .id(orderId)
                .createdAt(LocalDateTime.now())
                .totalPrice(BigDecimal.valueOf(200))
                .orderStatus(OrderStatus.ACCEPTED)
                .build();

        OrderPageUserDto orderPageUserDto = OrderPageUserDto.builder()
                .id(orderId)
                .createdAt(LocalDateTime.now())
                .totalPrice(BigDecimal.valueOf(200))
                .build();

        when(orderEntityService.findById(orderId)).thenReturn(order);
        when(orderMapper.mapToOrderPageUserDto(order)).thenReturn(orderPageUserDto);

        OrderPageUserDto result = orderUserService.getOrderPageForUser(orderId);

        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(BigDecimal.valueOf(200), result.getTotalPrice());

        verify(orderEntityService, times(1)).findById(orderId);
        verify(orderMapper, times(1)).mapToOrderPageUserDto(order);
    }

    @Test
    void testGetOrderPageForUser_OrderNotFound() {
        Long orderId = 1L;

        when(orderEntityService.findById(orderId)).thenThrow(new RuntimeException("Order not found"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderUserService.getOrderPageForUser(orderId);
        });

        assertEquals("Order not found", exception.getMessage());

        verify(orderEntityService, times(1)).findById(orderId);
        verify(orderMapper, never()).mapToOrderPageUserDto(any(Order.class));
    }
}
