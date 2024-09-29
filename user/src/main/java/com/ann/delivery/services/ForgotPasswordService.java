package com.ann.delivery.services;

import com.ann.delivery.dto.auth.ResetPasswordRequest;
import com.ann.delivery.dto.forgotten.password.ForgotPasswordRequest;
import com.ann.delivery.entity.User;
import com.ann.delivery.kafka.provider.notification.NotificationSender;
import com.ann.delivery.kafka.provider.notification.dto.UserForgotPasswordNotification;
import com.ann.delivery.util.TokenEncryptionUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final NotificationSender<UserForgotPasswordNotification> notificationSender;
    private final TokenEncryptionUtil tokenEncryptionUtil;
    private final UserEntityService userEntityService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private String generateForgotPasswordJwtToken(UserDetails userDetails) {
        log.debug("Generating forgot password JWT token for user: {}", userDetails.getUsername());
        return jwtService.generatePasswordRefreshToken(new HashMap<>(), userDetails);
    }

    public void sendPasswordResetEmail(ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.email();
        User user = userEntityService.getUserByEmail(email);

        if (user == null) {
            log.warn("User with email {} not found", email);
            throw new IllegalArgumentException("User not found");
        }

        String encryptedToken = tokenEncryptionUtil.encryptForgotPasswordToken(generateForgotPasswordJwtToken(user));

        log.info("Sending password reset email to: {}", email);
        notificationSender.send(UserForgotPasswordNotification.builder()
                .token(encryptedToken)
                .email(email)
                .build());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.token();

        log.debug("Decrypting reset password token");
        token = tokenEncryptionUtil.decryptForgotPasswordToken(token);

        log.debug("Decrypted JWT token: {}", token);
        try {
            String email = jwtService.extractUsername(token);
            User user = userEntityService.getUserByEmail(email);

            if (user == null) {
                log.warn("User with email {} not found during password reset", email);
                throw new IllegalArgumentException("User not found");
            }

            log.info("Resetting password for user: {}", email);
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.password()));
            log.info("Password successfully reset for user: {}", email);

        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", token);
            throw new JwtException("Your authentication token has expired");
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", token);
            throw new JwtException("Invalid token");
        }
    }
}
