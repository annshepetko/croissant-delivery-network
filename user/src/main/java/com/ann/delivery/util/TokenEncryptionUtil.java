package com.ann.delivery.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.html.Option;
import java.util.Base64;
import java.util.Optional;

@Component
public class TokenEncryptionUtil {

    private static final String SECRET_ENCRYPT_KEY = "Zg7aP7EYoyRnELLz/jf5wYii7Xcy4H+s3qvO/8Ni43g=";

    public String encryptForgotPasswordToken(String token) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(SECRET_ENCRYPT_KEY);

            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(token.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting JWT token", e);
        }

    }
    public String decryptForgotPasswordToken(String encryptedToken) {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(SECRET_ENCRYPT_KEY);

            SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedToken));

            return new String(decodedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting AES token", e);
        }
    }
}