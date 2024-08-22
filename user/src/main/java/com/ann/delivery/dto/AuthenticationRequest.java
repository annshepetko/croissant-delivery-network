package com.ann.delivery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record AuthenticationRequest(
        @Email
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
        String password) {
}
