package com.ann.delivery.services.auth;

import com.ann.delivery.factory.CookieFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieAuthService {


    public void addRefreshTokenToCookieResponse(String refreshToken, HttpServletResponse response){
        Cookie cookie = CookieFactory.createRefreshTokenCookie(refreshToken);
        addCookieToResponse(response, cookie);
    }
    private void addCookieToResponse(HttpServletResponse response, Cookie cookie){

        response.addCookie(cookie);
    }

}
