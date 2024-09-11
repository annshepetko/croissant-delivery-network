package com.ann.delivery.mapper;

import com.ann.delivery.dto.order.OrderDto;
import com.ann.delivery.dto.profile.UserProfilePage;
import com.ann.delivery.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserPageMapper {

    @Transactional
    public UserProfilePage buildUserProfile(User user, Page<OrderDto> orders){

        return UserProfilePage.builder()
                .userFirstname(user.getFirstname())
                .userLastname(user.getLastname())
                .numberOrders(user.getOrderIds().size())
                .orders(orders)
                .bonuses(user.getBonuses())
                .build();
    }

}
