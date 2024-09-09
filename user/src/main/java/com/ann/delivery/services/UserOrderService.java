package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.dto.UserDto;
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
    private final UserRepository userRepository;

    public Optional<UserDto> getUserIdIfPresent(String token) {
        User user = getUSer(token);
        log.info("user :: " + user.getEmail());
        return Optional.of(UserDto.builder()
                .bonuses(user.getBonuses())
                .email(user.getEmail())
                .build());
    }

    public Double getUSerBonuses(String token) {
        User user = getUSer(token);
        return user.getBonuses();
    }

    private User getUSer(String token){

        String email = jwtService.extractUsername(token.substring(7));

        return userRepository.findByEmail(email).orElseThrow();
    }
}
