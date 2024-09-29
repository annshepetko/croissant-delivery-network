package com.ann.delivery.services;

import com.ann.delivery.dto.order.OrderDto;
import com.ann.delivery.dto.profile.UserProfilePage;
import com.ann.delivery.entity.User;
import com.ann.delivery.mapper.UserPageMapper;
import com.ann.delivery.openFeign.client.OrderDataClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserPageService {

    private final OrderDataClient orderDataClient;
    private final UserPageMapper userPageMapper;

    @Transactional
    public UserProfilePage getUserProfile(HttpServletRequest request, Pageable pageable) {
        
        User user = (User) request.getAttribute("user");

        log.info("FETCHING USER`S ORDERS");
        Page<OrderDto> userOrders = orderDataClient.getAllUserOrders(user.getUsername(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        ).getBody().get();

        log.info("USER`S ORDERS RESPONSE : {}", userOrders);

        return userPageMapper.buildUserProfile(user, userOrders);
    }

}
