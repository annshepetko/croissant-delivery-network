package com.delivery.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PerformOrderRequest (

        List<OrderProductRequest> orderProductRequests,

        String userFirstname,

        String userLastname,

        String userPhoneNumber,

        AddressDto address,

        @JsonProperty(defaultValue = "0.0")
        double userBonuses,

        @JsonProperty(defaultValue = "false")
        boolean isWriteOffBonuses
)
{
}
