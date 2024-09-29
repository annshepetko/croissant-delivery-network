package com.ann.delivery.factory;

import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class CookieFactory {

    private static final Logger logger = LoggerFactory.getLogger(CookieFactory.class);

    public static Cookie createRefreshTokenCookie(String refreshToken){

        logger.info("CREATING REFRESH-COOKIE at: {}", LocalDateTime.now());

        Cookie cookie = new Cookie("Refresh-Token", refreshToken);

        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }


}
