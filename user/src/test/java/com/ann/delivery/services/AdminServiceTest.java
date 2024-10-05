package com.ann.delivery.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UserEntityService userEntityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsUserAdmin_whenUserIsAdmin() {

        String username = "admin_user";

        when(userEntityService.isUserAdmin(username)).thenReturn(true);

        Boolean result = adminService.isUserAdmin(username);

        assertEquals(true, result);
        verify(userEntityService, times(1)).isUserAdmin(username);
    }

    @Test
    void testIsUserAdmin_whenUserIsNotAdmin() {

        String username = "regular_user";

        when(userEntityService.isUserAdmin(username)).thenReturn(false);

        Boolean result = adminService.isUserAdmin(username);

        assertEquals(false, result);
        verify(userEntityService, times(1)).isUserAdmin(username);
    }
}
