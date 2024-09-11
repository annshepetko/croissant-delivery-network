package com.ann.delivery.dto.auth;

import com.ann.delivery.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @Size(min = 2, max = 10)
        String firstname,

        @Size(min = 2, max = 12)
        String lastname,

        @Email
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")
        String password,

        String defaultAddress,

        Roles role
) {
}
