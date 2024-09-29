package com.delivery.order.controller;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.exception.DeniedChangeStatusException;
import com.delivery.order.mapper.OrderAdminMapper;
import com.delivery.order.repo.OrderRepository;
import com.delivery.order.service.OrderAdminService;
import com.delivery.order.service.OrderEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(OrderAdminController.class)
@AutoConfigureMockMvc
class OrderAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderAdminService orderAdminService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderEntityService orderEntityService;

    @MockBean
    private OrderAdminMapper orderAdminMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllOrders_shouldReturnOrdersPage() throws Exception {
        OrderBaseDto orderBaseDto = new OrderBaseDto(1L, LocalDateTime.now(), BigDecimal.valueOf(100), OrderStatus.PREPARING);

        Pageable pageable = PageRequest.of(0, 25, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<OrderBaseDto> page = new PageImpl<>(List.of(orderBaseDto), pageable, 1);

        when(orderAdminService.getAllOrders(OrderStatus.PREPARING, pageable)).thenReturn(page);

        mockMvc.perform(get("/api/order/admin/all")
                        .param("status", "PREPARING")
                        .param("sorting", "createdAt")
                        .param("sortBy", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderAdminService, times(1)).getAllOrders(OrderStatus.PREPARING, pageable);
    }


    @Test
    void getOrderById_shouldReturnOrderPageAdminDto() throws Exception {

        Long orderId = 1L;
        OrderPageAdminDto orderPageAdminDto = new OrderPageAdminDto(
                orderId,
                LocalDateTime.now(),
                BigDecimal.valueOf(100),
                OrderStatus.PREPARING,
                List.of(), // Products
                "John",
                "Doe",
                "123456789",
                null // Address
        );

        when(orderAdminService.getOrderById(orderId)).thenReturn(orderPageAdminDto);

        mockMvc.perform(get("/api/order/admin/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderAdminService, times(1)).getOrderById(orderId);
    }

    @Test
    void changeOrderStatus_shouldUpdateOrderStatus() throws Exception {

        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.PREPARING;

        mockMvc.perform(patch("/api/order/admin/status/{id}", orderId)
                        .param("status", newStatus.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderAdminService, times(1)).changeOrderStatus(orderId, newStatus);
    }

    @Test
    void changeOrderStatus_shouldThrowExceptionWhenOrderIsDelivered() throws Exception {

        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.PREPARING;

        doThrow(new DeniedChangeStatusException("Order is already delivered"))
                .when(orderAdminService).changeOrderStatus(orderId, newStatus);

        mockMvc.perform(patch("/api/order/admin/status/{id}", orderId)
                        .param("status", newStatus.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(orderAdminService, times(1)).changeOrderStatus(orderId, newStatus);
    }
}
