package com.delivery.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PerformOrderRequest (

        @JsonProperty(required = true)
        @Size(min = 1, message = "Products must be greater than zero ")
        List<OrderProductRequest> orderProductRequests,

        @JsonProperty(required = true)
        String userFirstname,

        @JsonProperty(required = true)
        String userLastname,

        //different mobile operators
        @JsonProperty(required = true)
        @Pattern.List(
                @Pattern( regexp = "^\\+?3?8?(0(67|68|96|97|98)\\d{7})$"),
                @Pattern(regexp = "^\\+?3?8?(0[679]3\\d{7})$"),
                @Pattern(regexp = "^\\+?3?8?(0(66|95|99)\\d{7})$")
        )
        @Size(min = 10, max = 10)
        String userPhoneNumber,

        AddressDto address,

        @JsonProperty(defaultValue = "0.0")
        double userBonuses,

        @JsonProperty(defaultValue = "false")
        boolean isWriteOffBonuses
)
{
}
