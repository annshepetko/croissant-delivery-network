package com.delivery.order.mapper;

import com.delivery.order.dto.product.OrderProductDto;
import com.delivery.order.dto.user.OrderPageUserDto;
import com.delivery.order.entity.Address;
import com.delivery.order.entity.Order;
import com.delivery.order.entity.OrderedProduct;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.entity.status.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final AddressMapper addressMapper;

    public List<OrderedProduct> mapToOrderedProduct(List<OrderProductDto> requestList, Order order){
        return requestList.stream()
                .map(e -> OrderedProduct.builder()
                        .productId(e.productId())
                        .name(e.name())
                        .price(e.price())
                        .amount(e.amount())
                        .categoryId(e.categoryName())
                        .order(order)
                        .build())
                .toList();
    }

    public List<OrderProductDto> mapToOrderedProductRequests(List<OrderedProduct> requestList){
        return requestList.stream()
                .map(e -> OrderProductDto.builder()
                        .productId(e.getProductId())
                        .name(e.getName())
                        .price(e.getPrice())
                        .amount(e.getAmount())
                        .categoryName(e.getCategoryId())
                        .build())
                .toList();
    }

    public OrderPageUserDto mapToOrderPageUserDto(Order order) {

        return OrderPageUserDto.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .products(mapToOrderedProductRequests(order.getOrderedProducts()))
                .totalPrice(order.getTotalPrice())
                .status(order.getOrderStatus())
                .build();
    }
    public Order buildOrder(OrderBody orderBody) {
        Address address = addressMapper.mapToAddress(orderBody.getOrderRequest().address());

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .orderStatus(OrderStatus.ACCEPTED)
                .email(orderBody.getEmail())
                .totalPrice(orderBody.getPrice())
                .userLastName(orderBody.getOrderRequest().userFirstname())
                .userFirstName(orderBody.getOrderRequest().userFirstname())
                .userPhoneNumber(orderBody.getOrderRequest().userPhoneNumber())
                .build();


        order.setOrderedProducts(mapToOrderedProduct(orderBody.getOrderRequest().orderProductDtos(), order));
        address.setOrder(order);
        return order;
    }

}
