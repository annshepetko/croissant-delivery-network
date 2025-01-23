package com.ann.delivery.mapper;

import com.ann.delivery.dto.auth.RegisterRequest;
import com.ann.delivery.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User buildUserForAuthentication(RegisterRequest request) {
        return User.builder()
                .createdAt(LocalDateTime.now())
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
    }

}
