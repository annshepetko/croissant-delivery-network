package com.ann.delivery.services;

import com.ann.delivery.dto.order.UserOrderDto;
import com.ann.delivery.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class UserOrderServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserEntityService userEntityService;

    @InjectMocks
    private UserOrderService userOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserIdIfPresent() {

        String token = "Bearer test.jwt.token";
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);
        user.setBonuses(50.0);

        when(jwtService.extractUsername(token)).thenReturn(email);
        when(userEntityService.getUserByEmail(email)).thenReturn(user);

        Optional<UserOrderDto> result = userOrderService.getUserOrderIfPresent(token);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().email());
        assertEquals(50.0, result.get().bonuses());

        verify(jwtService, times(1)).extractUsername(anyString());
        verify(userEntityService, times(1)).getUserByEmail(email);
    }

    @Test
    void getUserBonuses() {

        String token = "Bearer test.jwt.token";
        String email = "test@example.com";

        User user = new User();

        user.setEmail(email);
        user.setBonuses(40.0);

        when(userEntityService.getUserByEmail(email)).thenReturn(user);
        when(jwtService.extractUsername(token)).thenReturn(email);

        assertEquals(user.getBonuses(), 40.0);
    }
}