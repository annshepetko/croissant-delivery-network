package com.ann.delivery.services.kafka;


import com.ann.delivery.UserRepository;
import com.ann.delivery.entity.OrderUser;
import com.ann.delivery.entity.User;
import com.ann.delivery.kafka.consumer.dto.UserNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserOrderListenerService {

    private final UserRepository userRepository;

    @Transactional
    public void handleOrderConfirmation(UserNotification userNotification){

        User user = userRepository.findByEmail(userNotification.email()).get();

        user.setBonuses(userNotification.bonuses());
        user.getOrderIds().add(OrderUser.builder()
                        .user(user)
                        .orderId(userNotification.orderId())
                .build());

    }

}
