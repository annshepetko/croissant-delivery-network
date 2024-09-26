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

    private String generateForgotPasswordJwtToken(
            UserDetails userDetails
    ) {
        String token = jwtService.generatePasswordRefreshToken(new HashMap<>(), userDetails);

        return token;
    }

    public void sendPasswordResetEmail(ForgotPasswordRequest forgotPasswordRequest) {

        String email = forgotPasswordRequest.email();
        User user = userEntityService.getUserByEmail(email);

        String encryptedToken = tokenEncryptionUtil.encryptForgotPasswordToken(generateForgotPasswordJwtToken(user));

        notificationSender.send(UserForgotPasswordNotification.builder()
                .token(encryptedToken)
                .email(email)
                .build());
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {

        String token = resetPasswordRequest.token();

        token = tokenEncryptionUtil.decryptForgotPasswordToken(token);
        log.info("ENTIRE JWT TOKEN:: " + token);
        try {
            String email = jwtService.extractUsername(token);
            User user = userEntityService.getUserByEmail(email);
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.password()));

        }catch (JwtException e){
            log.warn("EXPIRED JWT TOKEN:: " + token);
            throw new JwtException("Your authentication token has expired");
        }
    }

}
