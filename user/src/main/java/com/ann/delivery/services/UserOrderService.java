package com.ann.delivery.services;

import com.ann.delivery.dto.order.UserOrderDto;
import com.ann.delivery.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserOrderService {

    private final JwtService jwtService;

    private final UserEntityService userEntityService;
    public Optional<UserOrderDto> getUserIdIfPresent(String token) {
        User user = getUser(token);
        log.info("user :: " + user.getEmail());
        return Optional.of(UserOrderDto.builder()
                .bonuses(user.getBonuses())
                .email(user.getEmail())
                .build());
    }

    public Double getUSerBonuses(String token) {
        User user = getUser(token);
        return user.getBonuses();
    }

    private User getUser(String token){

        String email = jwtService.extractUsername(token.substring(7));

        return userEntityService.getUserByEmail(email);
    }
}
