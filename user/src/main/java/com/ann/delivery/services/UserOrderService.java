package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
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

    private final UserRepository userRepository;

    public Optional<UserOrderDto> getUserOrderIfPresent(String token) {

        Optional<User> user = userRepository.findByEmail(getUsername(token));

        if (user.isPresent()){
        log.info("user :: " + user.get().getEmail());
        return Optional.of(UserOrderDto.builder()
                .bonuses(user.get().getBonuses())
                .email(user.get().getEmail())
                .build());
        }
        return Optional.of(null);
    }

    public Double getUserBonuses(String token) {
        User user = userEntityService.getUserByEmail(getUsername(token));
        return user.getBonuses();
    }

    private String getUsername(String token){
        return jwtService.extractUsername(token.substring(7));
    }


}
