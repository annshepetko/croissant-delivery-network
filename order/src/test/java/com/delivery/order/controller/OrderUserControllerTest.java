package com.delivery.order.controller;

import com.delivery.order.dto.user.OrderPageUserDto;
import com.delivery.order.dto.user.OrderUserServiceDto;
import com.delivery.order.entity.status.OrderStatus;
import com.delivery.order.service.api.user.OrderUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderUserController.class)
@AutoConfigureMockMvc
class OrderUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderUserService orderUserService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUserOrders_shouldReturnOrdersPage() throws Exception {

        String userName = "user@example.com";
        Integer pageNumber = 0;
        Integer pageSize = 5;

        OrderUserServiceDto orderDto = OrderUserServiceDto.builder()
                .orderedAt(LocalDateTime.now())
                .id(1L)
                .totalPrice(BigDecimal.valueOf(100))
                .build();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<OrderUserServiceDto> page = new PageImpl<>(Collections.singletonList(orderDto), pageable, 1);

        when(orderUserService.getAllUserOrders(userName, pageable)).thenReturn(Optional.of(page));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/user/all/{name}", userName)
                        .param("pageNumber", String.valueOf(pageNumber))
                        .param("pageSize", String.valueOf(pageSize))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].totalPrice").value(100));

        verify(orderUserService, times(1)).getAllUserOrders(userName, pageable);
    }

    @Test
    void getOrderForUser() throws Exception {

        Long orderId = 1L;
        OrderPageUserDto orderPageUserDto = OrderPageUserDto.builder()
                .status(OrderStatus.ACCEPTED)
                .products(null)
                .id(orderId)
                .createdAt(null)
                .totalPrice(BigDecimal.valueOf(12.0))
                .build();

        when(orderUserService.getOrderPageForUser(orderId)).thenReturn(orderPageUserDto);

        String expectedJson = objectMapper.writeValueAsString(orderPageUserDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/order/user/" + orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(orderUserService, times(1)).getOrderPageForUser(orderId);
    }
}
