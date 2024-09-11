package com.ann.delivery.controller;

import com.ann.delivery.dto.auth.AuthenticationRequest;
import com.ann.delivery.dto.auth.AuthenticationResponse;
import com.ann.delivery.dto.auth.RegisterRequest;
import com.ann.delivery.dto.forgotten.password.ForgotPasswordRequest;
import com.ann.delivery.services.AuthenticationService;
import com.ann.delivery.services.ForgotPasswordService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final ForgotPasswordService forgotPasswordService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest,
            HttpServletResponse httpServletResponse
    ){

        return ResponseEntity.ok().body(authenticationService.register(registerRequest, httpServletResponse));
    }

    @PatchMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@CookieValue(name = "Refresh-Token") String refreshToken ){
        return ResponseEntity.ok(authenticationService.refreshAccessToken(refreshToken));
    }
    @PostMapping("/forgot")
    public void resetForgottenPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest){

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest, httpServletResponse));
    }
}
