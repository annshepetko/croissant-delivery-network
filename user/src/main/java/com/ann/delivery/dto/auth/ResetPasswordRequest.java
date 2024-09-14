package com.ann.delivery.dto.auth;

public record ResetPasswordRequest(
        String token,
        String password
) {
}
