package com.ann.delivery.controller;

import com.ann.delivery.dto.AuthenticationRequest;
import com.ann.delivery.dto.AuthenticationResponse;
import com.ann.delivery.dto.RegisterRequest;
import com.ann.delivery.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

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

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest, httpServletResponse));
    }
}
