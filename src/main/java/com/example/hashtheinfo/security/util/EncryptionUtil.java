package com.example.hashtheinfo.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Simple wrapper for password encoding. Use Spring's PasswordEncoder (BCrypt) under the hood.
 */
public class EncryptionUtil {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String hash(String plaintext) {
        return ENCODER.encode(plaintext);
    }

    public static boolean verify(String plaintext, String encoded) {
        return ENCODER.matches(plaintext, encoded);
    }
}
