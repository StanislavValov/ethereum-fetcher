package com.limechain.limeapi.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your_super_secret_key_with_at_least_256_bits";

    private final SecretKey key;

    public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        // You can add more roles/authorities here based on your needs
        return new UsernamePasswordAuthenticationToken(username, token, AuthorityUtils.NO_AUTHORITIES);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()  // Use parserBuilder instead of parser
                    .setSigningKey(key)  // Set the secret key
                    .build()  // Build the parser
                    .parseClaimsJws(token);  // Parse the JWT token
            return true;
        } catch (Exception e) {
            return false; // Invalid token
        }
    }
}
