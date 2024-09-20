package com.delivery.order.dto.admin;

import com.delivery.order.dto.address.AddressDto;
import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.entity.status.OrderStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Data
public class OrderPageAdminDto extends OrderBaseDto {

    private List<OrderProductDto> products;
    private String userFirstName;
    private String userLastName;
    private String userPhoneNumber;
    private AddressDto addressDto;

    public OrderPageAdminDto(
            Long id,
            LocalDateTime orderedAt,
            BigDecimal totalPrice,
            OrderStatus status,
            List<OrderProductDto> products,
            String userFirstName,
            String userLastName,
            String userPhoneNumber,
            AddressDto addressDto
    ) {
        super(id, orderedAt, totalPrice, status);
        this.products = products;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPhoneNumber = userPhoneNumber;
        this.addressDto = addressDto;
    }
}
