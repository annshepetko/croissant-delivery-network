package com.delivery.order.dto.admin;

import com.delivery.order.entity.status.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SuperBuilder
@Getter
public class OrderBaseDto {

        @JsonProperty
        private Long id;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        private LocalDateTime createdAt;

        @JsonProperty
        private BigDecimal totalPrice;

        @JsonProperty
        private OrderStatus status;

        public OrderBaseDto(Long id, LocalDateTime createdAt, BigDecimal totalPrice, OrderStatus status) {
                this.id = id;
                this.createdAt = createdAt;
                this.totalPrice = totalPrice;
                this.status = status;
        }
}
