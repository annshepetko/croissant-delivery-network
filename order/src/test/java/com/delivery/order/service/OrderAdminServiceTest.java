package com.delivery.order.service;

import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.Address;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.OrderedProduct;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.exception.DeniedChangeStatusException;
import com.delivery.order.mapper.OrderAdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class OrderAdminServiceTest {


    @Mock
    private OrderEntityService orderEntityService;

    @Mock
    private OrderAdminMapper orderAdminMapper;

    @InjectMocks
    private OrderAdminService orderAdminService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrderById() {
        Address address = new Address(); // Assuming Address has a default constructor
        List<OrderedProduct> orderedProducts = new ArrayList<>(); // Create a list of ordered products

        Order order = Order.builder()
                .orderStatus(OrderStatus.ACCEPTED)
                .id(1L)
                .orderedProducts(orderedProducts) // Replace with actual ordered products
                .userFirstName("ann")
                .userLastName("shh")
                .userPhoneNumber("0970549227")
                .address(address) // Replace with an actual Address object
                .email("example.email@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();


        OrderPageAdminDto orderPageAdminDto = OrderPageAdminDto. builder()
                .status(OrderStatus.ACCEPTED)
                .id(1L)
                .userFirstName("ann")
                .userLastName("shh")
                .userPhoneNumber("0970549227")
                .createdAt(LocalDateTime.now())
                .build();


        when(orderEntityService.findById(order.getId())).thenReturn(order);
        when(orderAdminMapper.mapToOrderAdminPage(order)).thenReturn(orderPageAdminDto);

        OrderPageAdminDto result = orderAdminService.getOrderById(order.getId());

        assertEquals(order.getId(), result.getId());
        assertEquals(order.getOrderStatus(), result.getStatus());
        assertEquals(order.getUserFirstName(), result.getUserFirstName());
        assertEquals(order.getUserLastName(), result.getUserLastName());
    }
    @Test
    void getOrderByIdWitchIsNotExist() {

        when(orderEntityService.findById(any(Long.class)))
                .thenThrow(new EntityNotFoundException("Order with this id is not exist"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            orderEntityService.findById(any(Long.class));
        });

        assertEquals("Order with this id is not exist", exception.getMessage());

    }
    @Test
    void changeOrderStatus() {
        Order order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.PREPARING)
                .build();
        when(orderEntityService.findById(1L)).thenReturn(order);

        orderAdminService.changeOrderStatus(1L, OrderStatus.DONE);

        assertEquals(OrderStatus.DONE, order.getOrderStatus());

    }
    @Test
    void changeOrderStatusFromDeliveredStatus() {
        Order order = Order.builder()
                .id(1L)
                .orderStatus(OrderStatus.DELIVERED)
                .build();
        when(orderEntityService.findById(1L)).thenReturn(order);

        assertThrows(DeniedChangeStatusException.class, () -> {
                    orderAdminService.changeOrderStatus(1L, OrderStatus.ACCEPTED);
        });
    }
    @Test
    void changeOrderStatusWithWrongId() {

        when(orderEntityService.findById(any(Long.class)))
                .thenThrow(new EntityNotFoundException("Order with this id is not exist"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            orderEntityService.findById(any(Long.class));
        });

        assertEquals("Order with this id is not exist", exception.getMessage());

    }
}