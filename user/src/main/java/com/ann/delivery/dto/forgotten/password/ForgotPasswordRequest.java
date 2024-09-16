package com.ann.delivery.dto.forgotten.password;

import jakarta.validation.constraints.Email;

public record ForgotPasswordRequest (

        @Email(message = "Email is incorrect")
        String email
) {
}
