package com.ann.delivery.controller;

import com.ann.delivery.auth.TestConfig;
import com.ann.delivery.entity.User;
import com.ann.delivery.enums.Roles;
import com.ann.delivery.services.AdminService;
import com.ann.delivery.services.JwtService;
import com.ann.delivery.services.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Import(TestConfig.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserEntityService userEntityService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AdminService adminService;
    @Test
    void isAdmin() throws Exception {

        User user = User.builder()
                .email("username.user@gmail.com") // Ensure the email is valid
                .role(Roles.ADMIN)
                .build();

        when(userEntityService.getUserByEmail(user.getEmail())).thenReturn(user);
        when(adminService.isUserAdmin(user.getEmail())).thenReturn(true);

        MockHttpServletRequestBuilder requestBuilder = get("/api/admin/is-admin")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token") // Add the authorization token
                .requestAttr("username", user.getEmail()); // Set the username attribute

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk()) // Expect 200 OK
                .andExpect(content().string("true")); // Expect response content to be "true"

        verify(adminService).isUserAdmin(user.getEmail());
    }

    @Test
    void isNotAdmin() throws Exception {
        String token = "Bearer token"; // Mock token

        when(adminService.isUserAdmin("username")).thenReturn(false);

        mockMvc.perform(get("/api/admin/is-admin")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .requestAttr("username", "username")) // Set the username attribute
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        verify(adminService).isUserAdmin("username");
    }
}
