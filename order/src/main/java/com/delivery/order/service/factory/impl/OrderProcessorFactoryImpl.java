package com.delivery.order.service.factory.impl;

import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.factory.OrderProcessorFactory;
import com.delivery.order.service.impl.order.AuthorizedOrderProcessor;
import com.delivery.order.service.impl.order.SimpleOrderProcessor;
import com.delivery.order.service.interfaces.OrderProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderProcessorFactoryImpl implements OrderProcessorFactory {

    private final ApplicationContext applicationContext;

    @Override
    public OrderProcessor getOrderProcessor(Optional<UserDto> userDto) {

        return handleProcessor(userDto);
    }

    private OrderProcessor handleProcessor(Optional<UserDto> user) {

        if (!user.isEmpty()) {
            return applicationContext.getBean(AuthorizedOrderProcessor.class);
        }
        return applicationContext.getBean(SimpleOrderProcessor.class);
    }
}

