package com.delivery.order.controller;

import com.delivery.order.dto.OrderRequest;
import com.delivery.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
class OrderControllerTest {

    @MockBean
    private OrderService orderService;


    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void orderProducts_shouldCallPerformOrder() throws Exception {

        OrderRequest orderRequest = createPerformOrderRequest();
        String token = "Bearer some-jwt-token";

        ObjectMapper objectMapper = new ObjectMapper();
        String mappedRequest = objectMapper.writeValueAsString(orderRequest);

        mockMvc.perform(post("/api/v1/order")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mappedRequest))
                .andExpect(status().isOk());

        verify(orderService, times(1)).performOrder(
                org.mockito.ArgumentMatchers.any(OrderRequest.class),
                org.mockito.ArgumentMatchers.eq(token)
        );
    }


    private OrderRequest createPerformOrderRequest() {
        return new OrderRequest(
                List.of(),
                "John",
                "Doe",
                "+380670123456",
                null,
                0.0,
                false
        );
    }
}
