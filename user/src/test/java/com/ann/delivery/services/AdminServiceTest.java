package com.ann.delivery.services;

import static org.mockito.Mockito.*;

import com.ann.delivery.entity.User;
import com.ann.delivery.enums.Roles;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {


    @Mock
    private JwtService jwtService;

    @Mock
    private UserEntityService userEntityService;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isUserAdmin() {

        String correctJwtToken = "correctJwtToken";

        String username = "ann_ssh";

        User user = new User();

        user.setEmail(username);
        user.setRole(Roles.ADMIN);

        when(jwtService.extractUsername(correctJwtToken)).thenReturn(username);
        when(userEntityService.getUserByEmail(username)).thenReturn(user);
        when(userEntityService.isUserAdmin(username)).thenReturn(true);


        Boolean response = adminService.isUserAdmin("Bearer " + correctJwtToken);

        assertTrue(response);
    }
    @Test
    void isUserNotAdmin() {

        String correctJwtToken = "correctJwtToken";

        String username = "ann_ssh";

        User user = new User();

        user.setEmail(username);
        user.setRole(Roles.CUSTOMER);

        when(jwtService.extractUsername(correctJwtToken)).thenReturn(username);
        when(userEntityService.getUserByEmail(username)).thenReturn(user);
        when(userEntityService.isUserAdmin(username)).thenReturn(false);


        Boolean response = adminService.isUserAdmin("Bearer " + correctJwtToken);

        assertFalse(response);
    }
    @Test
    void isUserAdminWithExpiredJwt() {

        String incorrectJwtToken = "invalid_JwtToken";

        String username = "ann_ssh";

        User user = new User();

        user.setEmail(username);
        user.setRole(Roles.ADMIN);

        when(jwtService.extractUsername(incorrectJwtToken)).thenThrow(new JwtException("jwt has expired"));
        when(userEntityService.getUserByEmail(username)).thenReturn(user);
        when(userEntityService.isUserAdmin(username)).thenReturn(true);

        JwtException jwtException = assertThrows(JwtException.class, () ->{
            adminService.isUserAdmin("Bearer " + incorrectJwtToken);
        });

        assertEquals("jwt has expired", jwtException.getMessage());
    }
}