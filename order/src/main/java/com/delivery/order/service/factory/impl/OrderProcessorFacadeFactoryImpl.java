package com.delivery.order.service.factory.impl;

import com.delivery.order.openFeign.dto.UserDto;
import com.delivery.order.service.factory.OrderProcessorFacadeFactory;
import com.delivery.order.service.impl.facade.AuthorizedOrderProcessorFacade;
import com.delivery.order.service.impl.facade.SimpleOrderProcessorFacade;
import com.delivery.order.service.impl.order.AuthorizedOrderProcessor;
import com.delivery.order.service.impl.order.SimpleOrderProcessor;
import com.delivery.order.service.interfaces.OrderProcessor;
import com.delivery.order.service.interfaces.facade.OrderProcessorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderProcessorFacadeFactoryImpl implements OrderProcessorFacadeFactory {

    private final ApplicationContext applicationContext;

    @Override
    public OrderProcessorFacade getOrderProcessor(Optional<UserDto> userDto) {

        return handleProcessor(userDto);
    }

    private OrderProcessorFacade handleProcessor(Optional<UserDto> user) {

        if (user.isPresent()) {
            return applicationContext.getBean(AuthorizedOrderProcessorFacade.class);
        }
        return applicationContext.getBean(SimpleOrderProcessorFacade.class);
    }
}

