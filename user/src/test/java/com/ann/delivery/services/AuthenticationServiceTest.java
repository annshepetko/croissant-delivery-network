package com.ann.delivery.services;

import com.ann.delivery.dto.auth.AuthenticationRequest;
import com.ann.delivery.dto.auth.AuthenticationResponse;
import com.ann.delivery.dto.auth.RegisterRequest;
import com.ann.delivery.entity.User;
import com.ann.delivery.enums.Roles;
import com.ann.delivery.factory.CookieFactory;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUserSuccessfully() {
        RegisterRequest request = new RegisterRequest("test@example.com", "First", "Last", "password", Roles.ADMIN);
        User user = new User();
        user.setEmail(request.email());
        user.setPassword("encoded password");
        user.setRole(Roles.ADMIN);

        when(userEntityService.isUserAlreadyRegistered(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encoded password");

        when(jwtService.generateToken(any(HashMap.class), any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(HashMap.class), any(User.class))).thenReturn("refreshToken");

        Cookie cookie = CookieFactory.createRefreshTokenCookie("refreshToken");
        doNothing().when(httpServletResponse).addCookie(cookie);

        AuthenticationResponse response = authenticationService.register(request, httpServletResponse);

        assertEquals("accessToken", response.accessToken());

        verify(userEntityService).saveUser(any(User.class));
        verify(httpServletResponse).addCookie(any(Cookie.class));
    }


    @Test
    void registerUserAlreadyExists() {
        RegisterRequest request = new RegisterRequest("test@example.com", "First", "Last", "password", Roles.ADMIN);

        when(userEntityService.isUserAlreadyRegistered(request.email())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> {
            authenticationService.register(request, httpServletResponse);
        });
    }

    @Test
    void refreshAccessTokenSuccessfully() {
        String refreshToken = "validRefreshToken";
        String username = "test@example.com";
        User userDetails = mock(User.class);

        when(jwtService.isTokenExpired(refreshToken)).thenReturn(false);
        when(jwtService.extractUsername(refreshToken)).thenReturn(username);
        when(userEntityService.getUserByEmail(username)).thenReturn(userDetails);
        when(jwtService.generateToken(any(), eq(userDetails))).thenReturn("newAccessToken");

        AuthenticationResponse response = authenticationService.refreshAccessToken(refreshToken);

        assertEquals("newAccessToken", response.accessToken());
    }

    @Test
    void refreshAccessTokenWithExpiredToken() {
        String refreshToken = "expiredRefreshToken";

        when(jwtService.isTokenExpired(refreshToken)).thenReturn(false);
        when(jwtService.extractUsername(refreshToken)).thenThrow(new SessionAuthenticationException("Session has expired"));
        SessionAuthenticationException sessionAuthenticationException = assertThrows(SessionAuthenticationException.class, () -> {
            authenticationService.refreshAccessToken(refreshToken);
        });

        assertEquals("Invalid refresh token", sessionAuthenticationException.getMessage() );
    }


    @Test
    void authenticateUserSuccessfully() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
        User user = mock(User.class);
        user.setPassword("onweo;rngo13rqcero");
        user.setEmail("exampleEmail@gmail.com");

        when(authenticationManager.authenticate(any())).thenReturn(null); // Mock authentication
        when(userEntityService.getUserByEmail(request.email())).thenReturn(user);
        when(jwtService.generateToken(any(), eq(user))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(), eq(user))).thenReturn("refreshToken");

        AuthenticationResponse response = authenticationService.authenticate(request, httpServletResponse);

        assertEquals("accessToken", response.accessToken());

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        verify(httpServletResponse).addCookie(cookieCaptor.capture());

        Cookie capturedCookie = cookieCaptor.getValue();
        assertEquals("Refresh-Token", capturedCookie.getName());
        assertEquals("refreshToken", capturedCookie.getValue());
    }

}
