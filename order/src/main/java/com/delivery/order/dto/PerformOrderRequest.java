package com.delivery.order.dto;

import java.util.List;

public record PerformOrderRequest (

        List<OrderProductRequest> orderProductRequests,
        String userFirstname,
        String userLastname,
        String userPhoneNumber,
        AddressDto address
)
{
}
