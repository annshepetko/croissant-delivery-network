package com.ann.delivery.factory;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;

public class CookieFactory {


    public static Cookie createRefreshTokenCookie(String refreshToken){
        Cookie cookie = new Cookie("Refresh-Token", refreshToken);

        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }


}
