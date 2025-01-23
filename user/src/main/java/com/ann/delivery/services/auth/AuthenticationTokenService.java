package com.ann.delivery.services.auth;


import com.ann.delivery.entity.User;
import com.ann.delivery.factory.CookieFactory;
import com.ann.delivery.mapper.UserMapper;
import com.ann.delivery.services.AuthenticationService;
import com.ann.delivery.services.JwtService;
import com.ann.delivery.services.UserEntityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationTokenService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenService.class);

    private final CookieService cookieService;
    private final JwtService jwtService;
    private final UserEntityService userEntityService;


    public String generateAccessToken(String username) {

        UserDetails userDetails = userEntityService.getUserByEmail(username);

        logger.info("New access token generated for user: {}", username);
        return jwtService.generateToken(new HashMap<>(), userDetails);
    }

    public Map<String, String> generateTokens(User user) {

        Map<String, String> authTokens = jwtService.buildTokens(user);

        logger.info("Generated access and refresh tokens for user: {}", user.getEmail());

        return authTokens;
    }

    public void setRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = CookieService.createRefreshTokenCookie(refreshToken);

        cookieService.addCookieToResponse(response, refreshTokenCookie);
    }

}
