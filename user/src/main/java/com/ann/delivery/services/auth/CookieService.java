package com.ann.delivery.services.auth;

import com.ann.delivery.factory.CookieFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class CookieService {


    private static final Logger logger = LoggerFactory.getLogger(CookieService.class);

    public static Cookie createRefreshTokenCookie(String refreshToken){

        logger.info("CREATING REFRESH-COOKIE at: {}", LocalDateTime.now());

        Cookie cookie = new Cookie("Refresh-Token", refreshToken);

        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public void addCookieToResponse(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
        logger.debug("Added cookie to response: {}", cookie.getName());
    }


}
