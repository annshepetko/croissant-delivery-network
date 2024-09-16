package com.ann.delivery.dto.auth;

import com.ann.delivery.enums.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;


public record RegisterRequest(

        @Size(min = 2, max = 10, message = "Name is too small")
        String firstname,

        @Size(min = 2, max = 12, message = "Lastname is too small")
        String lastname,

        @Email(message = "Email is incorrect")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",message = "Password is in wrong format")
        String password,

        Roles role
) {
}
