package com.javabootcamp.assessment2.utils;

import com.javabootcamp.assessment2.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JwtUtil {
    private static final String SECRET_KEY = "SECRET";

    public static String generateToken(User user, Date expirationDate) {
        HashMap<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getUsername(), expirationDate);
    }

    public static String getUsernameFromToken(String token) {
        var jwtModel = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parse(token);
        Claims claims = (Claims) jwtModel.getBody();
        return claims.getSubject();
    }

    private static String doGenerateToken(HashMap<String, Object> claims, String username, Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
