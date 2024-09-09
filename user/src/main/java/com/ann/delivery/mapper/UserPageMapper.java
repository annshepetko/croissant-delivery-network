package com.ann.delivery.mapper;

import com.ann.delivery.dto.OrderDto;
import com.ann.delivery.dto.UserProfilePage;
import com.ann.delivery.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UserPageMapper {

    @Transactional
    public UserProfilePage buildUserProfile(User user, List<OrderDto> orders){

        return UserProfilePage.builder()
                .userFirstname(user.getFirstname())
                .userLastname(user.getLastname())
                .numberOrders(user.getOrderIds().size())
                .orders(orders)
                .bonuses(user.getBonuses())
                .build();
    }

}
