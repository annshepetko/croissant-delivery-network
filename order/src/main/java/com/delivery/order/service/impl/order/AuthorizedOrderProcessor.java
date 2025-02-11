package com.delivery.order.service.impl.order;

import com.delivery.order.entity.Order;
import com.delivery.order.dto.Bonuses;
import com.delivery.order.dto.OrderBody;
import com.delivery.order.kafka.notification.CommonNotification;
import com.delivery.order.mapper.message.OrderAuthNotificationBuilder;
import com.delivery.order.service.interfaces.NotifiableOrderProcessor;
import com.delivery.order.service.interfaces.OrderProcessor;
import com.delivery.order.service.price.interfaces.PriceService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorizedOrderProcessor implements NotifiableOrderProcessor {

    private final SimpleOrderProcessor simpleOrderProcessor;
    private final OrderAuthNotificationBuilder notificationBuilder;
    private final PriceService authPriceService;
    private List<CommonNotification> notifications = new ArrayList<>();

    public AuthorizedOrderProcessor(

            @Qualifier("simpleOrderProcessor") OrderProcessor simpleOrderProcessor,
            OrderAuthNotificationBuilder authorizedProcessorNotificationBuilder,

            @Qualifier("authPriceService")
            PriceService authPriceService
    ) {
        this.simpleOrderProcessor = (SimpleOrderProcessor) simpleOrderProcessor;
        this.notificationBuilder = authorizedProcessorNotificationBuilder;
        this.authPriceService = authPriceService;
    }

    @Override
    public Order processOrder(OrderBody orderBody) {

        Bonuses bonuses = Bonuses.buildBonuses(orderBody);
        BigDecimal price = authPriceService.calculatePrice(orderBody, bonuses);

        updatePrice(orderBody, price);

        Order order = simpleOrderProcessor.processOrder(orderBody);

        notifications = createNotificationsToSend(order, bonuses);
        return order;
    }

    private void updatePrice(OrderBody orderBody, BigDecimal price) {
        orderBody.setPrice(price);
    }


    private List<CommonNotification> createNotificationsToSend(Order order, Bonuses refreshedBonuses) {

        return notificationBuilder.createNotifications(order, refreshedBonuses.getBonuses());
    }


    @Override
    public List<CommonNotification> getNotifications() {

        return notifications;
    }
}
