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

    @InjectMocks // This will automatically initialize userOrderService with the mocks
    private UserOrderService userOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    void getUserOrderIfPresent() {
        String email = "test@example.com";

        User user = User.builder()
                .email(email)
                .bonuses(50.0)
                .build();

        // Mock the behavior of userRepository
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Call the method under test
        Optional<UserOrderDto> result = userOrderService.getUserOrderIfPresent(email);

        // Assertions
        assertTrue(result.isPresent());
        assertEquals(email, result.get().email());
        assertEquals(50.0, result.get().bonuses());

        // Verify interactions
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getUserBonuses() {
        String email = "test@example.com";

        User user = new User();
        user.setEmail(email);
        user.setBonuses(40.0);

        // Mock the behavior of userEntityService
        when(userEntityService.getUserByEmail(email)).thenReturn(user);

        // Check bonuses
        assertEquals(40.0, user.getBonuses());
    }
}
