package com.ann.delivery.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record AuthenticationRequest(
        @Email(message = "Email is incorrect")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
        String password) {
}
