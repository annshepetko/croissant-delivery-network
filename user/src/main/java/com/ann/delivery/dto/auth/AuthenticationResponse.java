package com.ann.delivery.dto.auth;

import jakarta.servlet.http.Cookie;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

public record AuthenticationResponse(
       String accessToken
) {
}
