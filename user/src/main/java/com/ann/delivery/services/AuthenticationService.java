package com.ann.delivery.services;

import com.ann.delivery.dto.auth.AuthenticationRequest;
import com.ann.delivery.dto.auth.AuthenticationResponse;
import com.ann.delivery.dto.auth.RegisterRequest;
import com.ann.delivery.entity.User;
import com.ann.delivery.factory.CookieFactory;
import com.ann.delivery.mapper.UserMapper;
import com.ann.delivery.services.auth.AuthenticationTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserEntityService userEntityService;
    private final AuthenticationTokenService authenticationTokenService ;

    public AuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        logger.info("Attempting to register user: {}", request.email());

        if (userEntityService.isUserAlreadyRegistered(request.email())) {
            logger.error("User already registered: {} at {}", request.email(), LocalDateTime.now());
            throw new EntityExistsException("User already registered: " + request.email());
        }

        User user = userMapper.buildUserForAuthentication(request);
        return performRegistration(user, response);
    }


    private AuthenticationResponse performRegistration(User user, HttpServletResponse response) {

        User savedUser = userEntityService.saveUser(user);
        Map<String, String> authTokens = generateTokens(savedUser);
        addRefreshTokenToResponse(authTokens.get("refreshToken"), response);
        return buildResponse(authTokens);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {

        performAuthentication(request.email(), request.password());
        User user = userEntityService.getUserByEmail(request.email());

        Map<String, String> authTokens = generateTokens(user);
        addRefreshTokenToResponse(authTokens.get("refreshToken"), response);

        logger.info("Authentication successful for user: {}", request.email());
        return buildResponse(authTokens);
    }

    private void performAuthentication(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);

        try {
            logger.info("Authenticating user: {}", email);

            authenticationManager.authenticate(authentication);
            logger.info("User authenticated: {}", email);

        } catch (Exception e) {

            logger.error("Authentication failed for user: {} at {}", email, LocalDateTime.now());

            throw new SessionAuthenticationException("Invalid credentials");
        }
    }

    private Map<String, String> generateTokens(User user) {

        Map<String, String> authTokens = authenticationTokenService.generateTokens(user);

        return authTokens;
    }


    public AuthenticationResponse refreshAccessToken(String refreshToken) {

        String username;
        username = tryToRefreshAccessToken(refreshToken);

        String newAccessToken = authenticationTokenService.generateAccessToken(username);

        return new AuthenticationResponse(newAccessToken);
    }

    private String tryToRefreshAccessToken(String refreshToken) {
        logger.info("Refreshing access token at {}", LocalDateTime.now());

        try {
            if (jwtService.isTokenExpired(refreshToken)) {
                logger.warn("Refresh token expired at {}", LocalDateTime.now());
                throw new ExpiredJwtException(null, null, "Refresh token expired");
            }
            return jwtService.extractUsername(refreshToken);
        } catch (ExpiredJwtException e) {
            logger.error("Refresh token expired: {} at {}", refreshToken, LocalDateTime.now());
            throw new SessionAuthenticationException("Session expired, please login again");

        } catch (SessionAuthenticationException e) {
            logger.error("Error while refreshing access token at {}: {}", LocalDateTime.now(), e.getMessage());
            throw new SessionAuthenticationException("Invalid refresh token");

        }
    }

    private void addRefreshTokenToResponse(String refreshToken, HttpServletResponse response){
        Cookie cookie = CookieFactory.createRefreshTokenCookie(refreshToken);

        response.addCookie(cookie);
    }

    private AuthenticationResponse buildResponse(Map<String, String> authTokens) {
        return new AuthenticationResponse(authTokens.get("accessToken"));
    }
}
