package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.dto.OrderDto;
import com.ann.delivery.dto.UserProfilePage;
import com.ann.delivery.entity.User;
import com.ann.delivery.mapper.UserPageMapper;
import com.ann.delivery.openFeign.client.OrderDataClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPageService {

    private final OrderDataClient orderDataClient;
    private final UserPageMapper userPageMapper;

    @Transactional
    public UserProfilePage getUserProfile(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");


        Hibernate.initialize(user.getOrderIds());

        List<OrderDto> userOrders = orderDataClient.getAllUserOrders(user.getUsername()).getBody().get();
        return userPageMapper.buildUserProfile(user, userOrders);


    }

}
