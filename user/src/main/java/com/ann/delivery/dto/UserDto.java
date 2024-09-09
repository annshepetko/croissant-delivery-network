package com.ann.delivery.dto;

import lombok.Builder;

@Builder
public record UserDto (
        String email,
        Double bonuses

) {
}
