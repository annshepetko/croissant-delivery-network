package com.delivery.product.dto;


import lombok.Builder;

import java.io.Serializable;

@Builder
public record CategoryDto(
        int id,
        String name
) implements Serializable {
}
