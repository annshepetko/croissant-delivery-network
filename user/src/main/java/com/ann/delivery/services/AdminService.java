package com.ann.delivery.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final UserEntityService userEntityService;
    private final JwtService jwtService;

    public Boolean isUserAdmin(String token){

        String username = jwtService.extractUsername(token.substring(7));

        log.info("USER-ADMIN-NAME:: " + username);
        return userEntityService.isUserAdmin(username);
    }
}
