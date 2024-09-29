package com.ann.delivery.controller;

import com.ann.delivery.dto.auth.AuthenticationRequest;
import com.ann.delivery.dto.auth.AuthenticationResponse;
import com.ann.delivery.dto.auth.RegisterRequest;
import com.ann.delivery.dto.auth.ResetPasswordRequest;
import com.ann.delivery.dto.forgotten.password.ForgotPasswordRequest;
import com.ann.delivery.services.AuthenticationService;
import com.ann.delivery.services.ForgotPasswordService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.LambdaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(LambdaUtil.class);

    private final ForgotPasswordService forgotPasswordService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest,
            HttpServletResponse httpServletResponse
    ){

        logger.info("NEW SMISHARIK: {}", registerRequest.email());

        return ResponseEntity.ok()
                .body(authenticationService.register(registerRequest, httpServletResponse));
    }

    @PatchMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@CookieValue(name = "Refresh-Token") String refreshToken ){

        logger.info("TRYING TO REFRESH ACCESS TOKEN at: {}", LocalDateTime.now());

        return ResponseEntity.ok(authenticationService.refreshAccessToken(refreshToken));
    }
    @PostMapping("/forgot")
    public void resetForgottenPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest){

        logger.info("TRYING TO RECEIVE RESET-PASSWORD LINK at : {}", LocalDateTime.now());

        forgotPasswordService.sendPasswordResetEmail(forgotPasswordRequest);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse){

        logger.info("AUTHENTICATE USER : " + authenticationRequest.email() + "at : {}", LocalDateTime.now());

        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest, httpServletResponse));
    }

    @PatchMapping("/reset-password")
    public void resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest){

        logger.info("RESET PASSWORD AT : {}", LocalDateTime.now());

        forgotPasswordService.resetPassword(resetPasswordRequest);

    }
}
