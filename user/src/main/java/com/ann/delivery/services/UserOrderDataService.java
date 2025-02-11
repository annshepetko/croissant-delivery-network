package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.dto.order.UserOrderDto;
import com.ann.delivery.entity.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOrderDataService {

    private static final Logger log = LoggerFactory.getLogger(UserOrderDataService.class);
    private final UserRepository userRepository;

    public Optional<UserOrderDto> getUserOrderIfPresent(String username) {

        log.debug("Searching for user by username: {}", username);

        Optional<User> userOpt = userRepository.findByEmail(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            log.info("User found: {}", user.getEmail());

            UserOrderDto userOrderDto = UserOrderDto.builder()
                    .bonuses(user.getBonuses())
                    .email(user.getEmail())
                    .build();

            log.debug("UserOrderDto created for user: {}", user.getEmail());
            return Optional.of(userOrderDto);
        } else {
            log.warn("User not found: {}", username);
            return Optional.empty();
        }
    }

    public Double getUserBonuses(User user) {
        log.debug("Fetching bonuses for user: {}", user.getEmail());
        Double bonuses = user.getBonuses();
        log.info("User {} has bonuses: {}", user.getEmail(), bonuses);
        return bonuses;
    }
}
