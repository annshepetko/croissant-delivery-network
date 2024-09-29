package com.ann.delivery.services;

import com.ann.delivery.dto.auth.AuthenticationRequest;
import com.ann.delivery.dto.auth.AuthenticationResponse;
import com.ann.delivery.dto.auth.RegisterRequest;
import com.ann.delivery.entity.User;
import com.ann.delivery.factory.CookieFactory;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityService userEntityService;

    public AuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        logger.info("Attempting to register user: {}", request.email());

        if (userEntityService.isUserAlreadyRegistered(request.email())) {
            logger.error("User already registered: {} at {}", request.email(), LocalDateTime.now());
            throw new EntityExistsException("User already registered: " + request.email());
        }

        User newUser = buildUser(request);
        logger.info("User built for registration: {}", newUser.getEmail());

        return performAuthentication(newUser, response);
    }

    private User buildUser(RegisterRequest request) {
        return User.builder()
                .createdAt(LocalDateTime.now())
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
    }

    private AuthenticationResponse performAuthentication(User user, HttpServletResponse response) {
        logger.info("Saving new user: {}", user.getEmail());
        userEntityService.saveUser(user);

        String accessToken = jwtService.generateToken(new HashMap<>(), user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        Cookie refreshTokenCookie = CookieFactory.createRefreshTokenCookie(refreshToken);
        addCookieToResponse(response, refreshTokenCookie);

        logger.info("Generated access and refresh tokens for user: {}", user.getEmail());
        return new AuthenticationResponse(accessToken);
    }

    public AuthenticationResponse refreshAccessToken(String refreshToken) {
        logger.info("Refreshing access token at {}", LocalDateTime.now());

        String username;
        try {
            if (jwtService.isTokenExpired(refreshToken)) {
                logger.warn("Refresh token expired at {}", LocalDateTime.now());
                throw new ExpiredJwtException(null, null, "Refresh token expired");
            }
            username = jwtService.extractUsername(refreshToken);
        } catch (ExpiredJwtException e) {
            logger.error("Refresh token expired: {} at {}", refreshToken, LocalDateTime.now());
            throw new SessionAuthenticationException("Session expired, please login again");
        } catch (Exception e) {
            logger.error("Error while refreshing access token at {}: {}", LocalDateTime.now(), e.getMessage());
            throw new SessionAuthenticationException("Invalid refresh token");
        }

        UserDetails userDetails = userEntityService.getUserByEmail(username);
        String newAccessToken = jwtService.generateToken(new HashMap<>(), userDetails);

        logger.info("New access token generated for user: {}", username);
        return new AuthenticationResponse(newAccessToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        logger.info("Authenticating user: {}", request.email());
        authenticateUser(request.email(), request.password());

        UserDetails userDetails = userEntityService.getUserByEmail(request.email());
        String accessToken = jwtService.generateToken(new HashMap<>(), userDetails);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userDetails);

        Cookie refreshTokenCookie = CookieFactory.createRefreshTokenCookie(refreshToken);
        addCookieToResponse(response, refreshTokenCookie);

        logger.info("Authentication successful for user: {}", request.email());
        return new AuthenticationResponse(accessToken);
    }

    private void authenticateUser(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            logger.info("User authenticated: {}", email);
        } catch (Exception e) {
            logger.error("Authentication failed for user: {} at {}", email, LocalDateTime.now());
            throw new SessionAuthenticationException("Invalid credentials");
        }
    }

    private void addCookieToResponse(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
        logger.debug("Added cookie to response: {}", cookie.getName());
    }
}
