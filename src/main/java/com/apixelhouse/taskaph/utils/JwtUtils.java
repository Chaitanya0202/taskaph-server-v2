package com.apixelhouse.taskaph.utils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // Generate a strong secret key using io.jsonwebtoken.security.Keys
    private String SECRET_KEY;

    // constructor 
    public JwtUtils() {
        // Generate a secure key of at least 256 bits
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SECRET_KEY = Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().claims().add(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 20 * 1000)) // 1 hour expiration
                .and()
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
