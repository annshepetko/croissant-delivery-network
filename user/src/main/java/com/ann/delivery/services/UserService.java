package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.entity.BaseEntity;
import com.ann.delivery.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Optional<Long> getUserIdIfPresent(String token){
        Optional<User> user = userRepository.findByEmail(jwtService.extractUsername(token.substring(7)));
        log.info("user :: " + user.get().getId());
        return Optional.of(user.get().getId());
    }

}
