package com.ann.delivery.controller;

import com.ann.delivery.auth.TestConfig;
import com.ann.delivery.dto.order.UserOrderDto;
import com.ann.delivery.entity.User;
import com.ann.delivery.services.JwtService;
import com.ann.delivery.services.UserOrderDataService;
import com.ann.delivery.services.UserPageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
@Import(TestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserOrderDataService userOrderDataService;


    @MockBean
    private UserPageService userPageService;

    @MockBean
    private JwtService jwtService;

    @Test
    void getUserBonuses() throws Exception {
        User username = User.builder()
                .bonuses(52.0)
                .build();

        when(userOrderDataService.getUserBonuses(username)).thenReturn(52.0);

        MockHttpServletRequestBuilder requestBuilder = get("/api/user/bonuses")
                .requestAttr("user", username);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("52.0"));

        verify(userOrderDataService, times(1)).getUserBonuses(username);
    }


    @Test
    void isUserRegistered() throws Exception {
        UserOrderDto userOrderDto = new UserOrderDto("ann_shh@gmail.com", 52.0);

        when(userOrderDataService.getUserOrderIfPresent("ann_shh@gmail.com")).thenReturn(Optional.of(userOrderDto));

        ObjectMapper objectMapper = new ObjectMapper();
        String expectedJson = objectMapper.writeValueAsString(userOrderDto);

        MockHttpServletRequestBuilder requestBuilder = get("/api/user/is-registered")
                .requestAttr("username", "ann_shh@gmail.com");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));

        verify(userOrderDataService, times(1)).getUserOrderIfPresent("ann_shh@gmail.com");
    }


    @Test
    void getUserProfile() {
    }
}