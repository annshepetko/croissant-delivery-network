package com.delivery.order.mapper;

import com.delivery.order.dto.admin.OrderBaseDto;
import com.delivery.order.dto.admin.OrderPageAdminDto;
import com.delivery.order.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAdminMapper {

    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;

    public OrderPageAdminDto mapToOrderAdminPage(Order order){
        return OrderPageAdminDto.builder()

                .userPhoneNumber(order.getUserPhoneNumber())
                .addressDto(addressMapper.mapToAddressDto(order.getAddress()))
                .userLastName(order.getUserLastName())
                .userFirstName(order.getUserFirstName())
                .products(orderMapper.mapToOrderedProductRequests(order.getOrderedProducts()))
                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalPrice())
                .status(order.getOrderStatus())
                .id(order.getId())
                .build();
    }

    public OrderBaseDto mapToAdminDto(Order order){

        return OrderBaseDto.builder()

                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalPrice())
                .status(order.getOrderStatus())
                .id(order.getId())
                .build();
    }
}
