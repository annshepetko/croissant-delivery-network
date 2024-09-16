package com.ann.delivery.dto.auth;

import jakarta.validation.constraints.Pattern;

public record ResetPasswordRequest(
        String token,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",message = "Password is in wrong format")
        String password
) {
}
