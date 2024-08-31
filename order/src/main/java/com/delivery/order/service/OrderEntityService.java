package com.delivery.order.service;

import com.delivery.order.entity.Address;
import com.delivery.order.entity.Order;
import com.delivery.order.mapper.AddressMapper;
import com.delivery.order.mapper.OrderMapper;
import com.delivery.order.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderEntityService {

    private final OrderRepository orderRepository;
    private final AddressMapper addressMapper;
    private final OrderMapper orderMapper;

    @Transactional
    public void saveOrder(OrderMapper.OrderBody orderBody){

        Address address = addressMapper.mapToAddress(orderBody.getPerformOrderRequest().address());

        Order order = Order.builder()
                .userId(orderBody.getUserId())
                .totalPrice(orderBody.getPrice())
                .userLastName(orderBody.getPerformOrderRequest().userFirstname())
                .userFirstName(orderBody.getPerformOrderRequest().userFirstname())
                .userPhoneNumber(orderBody.getPerformOrderRequest().userPhoneNumber())
                .build();


        order.setOrderedProducts(orderMapper.mapToOrderedProduct(orderBody.getPerformOrderRequest().orderProductRequests(), order));
        address.setOrder(order);

         orderRepository.save(order);
    }
}