package com.delivery.order.dto.user;

import com.delivery.order.dto.address.AddressDto;
import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.status.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Order}
 */
@Builder
public record OrderUserServiceDto(

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        LocalDateTime orderedAt,
        Long id,
        BigDecimal totalPrice
) {
}