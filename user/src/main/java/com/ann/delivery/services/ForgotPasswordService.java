package com.ann.delivery.services;

import com.ann.delivery.dto.forgotten.password.ForgotPasswordRequest;
import com.ann.delivery.entity.User;
import com.ann.delivery.kafka.provider.notification.NotificationSender;
import com.ann.delivery.kafka.provider.notification.dto.UserForgotPasswordNotification;
import com.ann.delivery.util.TokenEncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final NotificationSender<UserForgotPasswordNotification> notificationSender;
    private final TokenEncryptionUtil tokenEncryptionUtil;
    private final UserEntityService userEntityService;
    private final JwtService jwtService;

    private String generateForgotPasswordJwtToken(
            UserDetails userDetails
    ){
        String token = jwtService.generatePasswordRefreshToken(new HashMap<>(), userDetails);

        return token;
    }

    public void prepareResetPassword(ForgotPasswordRequest forgotPasswordRequest){

        String email = forgotPasswordRequest.email();
        User user = userEntityService.getUserByEmail(email);

        String encryptedToken = tokenEncryptionUtil.encryptForgotPasswordToken(generateForgotPasswordJwtToken(user));

        notificationSender.send(UserForgotPasswordNotification.builder()
                        .token(encryptedToken)
                        .email(email)
                .build());
    }

}
