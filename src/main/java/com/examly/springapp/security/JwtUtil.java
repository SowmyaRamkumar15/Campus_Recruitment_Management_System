package com.examly.springapp.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key;

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

    public JwtUtil(@Value("${jwt.secret}") String secret) {

        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(String email, String role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return extractClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}


// package com.examly.springapp.security;

// import io.jsonwebtoken.*;
// import io.jsonwebtoken.security.Keys;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;

// import java.security.Key;
// import java.util.Date;

// @Component
// public class JwtUtil {

//     private final Key key;

//     // Access token expiration (1 hour)
//     private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60;

//     // Refresh token expiration (7 days)
//     private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

//     // Inject secret from application.properties
//     public JwtUtil(@Value("${jwt.secret}") String secret) {

//         if (secret.length() < 32) {
//             throw new IllegalArgumentException("JWT secret must be at least 32 characters");
//         }

//         this.key = Keys.hmacShaKeyFor(secret.getBytes());
//     }

//     // Generate Access Token
//     public String generateAccessToken(String email, String role) {

//         return Jwts.builder()
//                 .setSubject(email)
//                 .claim("role", role)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     // Generate Refresh Token
//     public String generateRefreshToken(String email) {

//         return Jwts.builder()
//                 .setSubject(email)
//                 .setIssuedAt(new Date())
//                 .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();
//     }

//     // Extract email
//     public String getEmailFromToken(String token) {
//         return getClaims(token).getSubject();
//     }

//     // Extract role
//     public String getRoleFromToken(String token) {
//         return (String) getClaims(token).get("role");
//     }

//     // Check expiration
//     public boolean isTokenExpired(String token) {
//         return getClaims(token).getExpiration().before(new Date());
//     }

//     // Parse token
//     private Claims getClaims(String token) {

//         return Jwts.parserBuilder()
//                 .setSigningKey(key)
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();
//     }
// }