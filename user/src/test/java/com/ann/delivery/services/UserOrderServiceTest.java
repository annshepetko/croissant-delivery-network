package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
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
    private UserRepository userRepository;

    @Mock
    private UserEntityService userEntityService;

    @InjectMocks
    private UserOrderService userOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserOrderIfPresent() {
        String email = "test@example.com";

        User user = User.builder()
                .email(email)
                .bonuses(50.0)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<UserOrderDto> result = userOrderService.getUserOrderIfPresent(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().email());
        assertEquals(50.0, result.get().bonuses());

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getUserBonuses() {
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);
        user.setBonuses(40.0);

        when(userEntityService.getUserByEmail(email)).thenReturn(user);

        assertEquals(40.0, user.getBonuses());
    }
}
