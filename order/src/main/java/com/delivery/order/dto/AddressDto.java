package com.delivery.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

/**
 * DTO for {@link com.delivery.order.entity.Address}
 */

@Valid
public record AddressDto(

        String city,
        String district,
        String street,
        String house,
        String entrance,
        int floor,
        @Positive(message = "Flat number must be greater then 0")
        int flat
){}