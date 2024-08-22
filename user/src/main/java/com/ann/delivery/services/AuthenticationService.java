package com.ann.delivery.services;

import com.ann.delivery.UserRepository;
import com.ann.delivery.dto.AuthenticationRequest;
import com.ann.delivery.dto.AuthenticationResponse;
import com.ann.delivery.dto.RegisterRequest;
import com.ann.delivery.entity.User;
import com.ann.delivery.factory.CookieFactory;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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


    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthenticationResponse register(RegisterRequest request, HttpServletResponse httpServletResponse) {

        if(isUserAlreadyExist(request.email())){
            throw new EntityExistsException("User already registered");
        }
        User userDetails = (User) buildUserModel(request);

        return authDispatcher(userDetails, httpServletResponse);

    }

    private UserDetails buildUserModel(RegisterRequest request) {
        UserDetails userDetails = User.builder()
                .createdAt(LocalDateTime.now())
                .email(request.email())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
        return userDetails;
    }

    private boolean isUserAlreadyExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    private AuthenticationResponse authDispatcher(User user, HttpServletResponse httpServletResponse) {

        userRepository.save(user);

        String accessToken = jwtService.generateToken(new HashMap<>(), user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        Cookie cookie = CookieFactory.createRefreshTokenCookie(refreshToken);

        setCookieToResponse(httpServletResponse, cookie);

        return new AuthenticationResponse(accessToken);
    }

    private static void setCookieToResponse(HttpServletResponse httpServletResponse, Cookie cookie) {
        httpServletResponse.addCookie(cookie);
    }

    public AuthenticationResponse refreshAccessToken(String refreshToken) {
        String username = null;

        try {
            if (!jwtService.isTokenExpired(refreshToken)) {
                username = jwtService.extractUsername(refreshToken);
            }

        } catch (ExpiredJwtException e) {
            throw new SessionAuthenticationException("Session has expired");
        }
        UserDetails user = userRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("User with this email not found"));
        String accessToken = jwtService.generateToken(new HashMap<>(), user);

        return new AuthenticationResponse(accessToken);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse) {

        authenticateUser(authenticationRequest.email(), authenticationRequest.password());

        UserDetails userDetails = getUserDetails(authenticationRequest.email());

        String accessToken = jwtService.generateToken(new HashMap<>(), userDetails);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), userDetails);

        Cookie refreshCookie = CookieFactory.createRefreshTokenCookie(refreshToken);

        setCookieToResponse(httpServletResponse, refreshCookie);

        return new AuthenticationResponse(accessToken);

    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )

        );
    }

    private UserDetails getUserDetails(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with this email not found"));
    }

}
