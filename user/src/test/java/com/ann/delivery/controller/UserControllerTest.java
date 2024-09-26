package com.ann.delivery.controller;

import com.ann.delivery.auth.TestConfig;
import com.ann.delivery.dto.order.UserOrderDto;
import com.ann.delivery.services.JwtService;
import com.ann.delivery.services.UserOrderService;
import com.ann.delivery.services.UserPageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
@Import(TestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserOrderService userOrderService;


    @MockBean
    private UserPageService userPageService;

    @MockBean
    private JwtService jwtService;

    @Test
    void getUserBonuses() throws Exception {

        String token = "Bearer token";

        when(userOrderService.getUserBonuses(token)).thenReturn(52.0);

        mockMvc.perform(get("/api/user/bonuses")
                        .header(HttpHeaders.AUTHORIZATION, token)
                ).andExpect(status().isOk())
                .andExpect(content().string("52.0"));

    }

    @Test
    void isUserRegistered() throws Exception {
        String jwtToken = "Bearer jwt";
        Optional<UserOrderDto> userOrderDto = Optional.of(
                new UserOrderDto("ann_shh@gmail.com", 52.0)
        );

        when(userOrderService.getUserOrderIfPresent(jwtToken)).thenReturn(userOrderDto);

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(userOrderDto.get());

        mockMvc.perform(get("/api/user/is-registered")
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

    }

    @Test
    void getUserProfile() {
    }
}