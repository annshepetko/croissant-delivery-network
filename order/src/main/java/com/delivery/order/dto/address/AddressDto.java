package com.delivery.order.dto.address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link com.delivery.order.entity.Address}
 */

@Valid
@Builder
public record AddressDto(

        Long id,
        String city,
        String district,
        String street,
        String house,
        String entrance,
        int floor,
        @Positive(message = "Flat number must be greater then 0")
        int flat
){}