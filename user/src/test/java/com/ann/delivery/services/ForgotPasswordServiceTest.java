package com.ann.delivery.services;

import com.ann.delivery.dto.auth.ResetPasswordRequest;
import com.ann.delivery.dto.forgotten.password.ForgotPasswordRequest;
import com.ann.delivery.entity.User;
import com.ann.delivery.kafka.provider.notification.NotificationSender;
import com.ann.delivery.kafka.provider.notification.dto.UserForgotPasswordNotification;
import com.ann.delivery.util.TokenEncryptionUtil;
import io.jsonwebtoken.JwtException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ForgotPasswordServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private NotificationSender<UserForgotPasswordNotification> notificationSender;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private TokenEncryptionUtil tokenEncryptionUtil;

    @InjectMocks
    private ForgotPasswordService forgotPasswordService;

    private final String jwtToken =  "Jwt token";
    private final String expiredJwtToken =  "Jwt token";
    private final String encryptedToken =  "Jwt token";
    private final String incorrectEmail = "incorrect email";
    private final String correctEmail = "correct email";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void prepareResetPassword() {

        User user = new User();
        user.setEmail(correctEmail);

        when(jwtService.generatePasswordRefreshToken(new HashMap<>(), user)).thenReturn(jwtToken);
        when(userEntityService.getUserByEmail(correctEmail)).thenReturn(user);
        when(tokenEncryptionUtil.encryptForgotPasswordToken(jwtToken)).thenReturn(encryptedToken);

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(correctEmail);

        forgotPasswordService.sendPasswordResetEmail(forgotPasswordRequest);

        verify(userEntityService).getUserByEmail(correctEmail); // перевіряємо, що викликано пошук користувача по email
        verify(tokenEncryptionUtil).encryptForgotPasswordToken(anyString()); // перевіряємо, що токен був зашифрований
        verify(notificationSender).send(any(UserForgotPasswordNotification.class));

    }

    @Test
    void prepareResetPasswordExceptionThrow() {

        when(userEntityService.getUserByEmail(incorrectEmail)).thenThrow(new EntityNotFoundException("User with this emil does`nt exist"));

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest(incorrectEmail);


        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            forgotPasswordService.sendPasswordResetEmail(forgotPasswordRequest);
        });

        Assertions.assertEquals("User with this emil does`nt exist", exception.getMessage());

    }


    @Test
    void resetPassword() {

        User user = new User();

        user.setPassword("1o23j9hf9v*()#JE0jc1c");
        user.setEmail(correctEmail);

        String newPassword = "cpqj3j4qjkfcpo,f-3rkcer";

        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(encryptedToken, newPassword);


        when(tokenEncryptionUtil.decryptForgotPasswordToken(encryptedToken)).thenReturn(jwtToken);
        when(jwtService.extractUsername(jwtToken)).thenReturn(correctEmail);
        when(userEntityService.getUserByEmail(correctEmail)).thenReturn(user);
        when(passwordEncoder.encode(resetPasswordRequest.password())).thenReturn(newPassword);

        forgotPasswordService.resetPassword(resetPasswordRequest);


        assertEquals(user.getPassword(), newPassword);

        verify(passwordEncoder, times(1)).encode(resetPasswordRequest.password());
        verify(userEntityService).getUserByEmail(correctEmail);

    }



}

