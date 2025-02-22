package com.ann.delivery.services.auth;


import com.ann.delivery.entity.User;
import com.ann.delivery.services.JwtService;
import com.ann.delivery.services.UserEntityService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenService.class);

    private final JwtService jwtService;
    private final UserEntityService userEntityService;
    private final CookieAuthService cookieService;


    public String generateAccessToken(String username) {

        UserDetails userDetails = userEntityService.getUserByEmail(username);

        logger.info("New access token generated for user: {}", username);
        return jwtService.generateAccessToken(new HashMap<>(), userDetails);
    }

    public Map<String, String> generateTokens(User user) {

        Map<String, String> authTokens = jwtService.buildTokens(user);

        logger.info("Generated access and refresh tokens for user: {}", user.getEmail());

        return authTokens;
    }

    public Map<String, String> performTokensManaging(HttpServletResponse response, User user) {

        Map<String, String> authTokens = generateTokens(user);

        cookieService.addRefreshTokenToCookieResponse(authTokens.get("refreshToken"), response);

        return authTokens;
    }

}
