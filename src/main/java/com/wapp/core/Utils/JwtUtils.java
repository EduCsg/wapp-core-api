package com.wapp.core.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private static final SecretKey SECRET_KEY = loadSecretKey();

    private static SecretKey loadSecretKey() {
        String rawKey = System.getenv("JWT_SECRET_KEY");

        if (rawKey == null || rawKey.isBlank())
            throw new IllegalStateException("JWT_SECRET_KEY não foi definido nas variáveis de ambiente.");

        try {
            return Keys.hmacShaKeyFor(Base64.getDecoder().decode(rawKey));
        } catch (IllegalArgumentException e) {
            byte[] rawBytes = rawKey.getBytes(StandardCharsets.UTF_8);
            if (rawBytes.length < 32) throw new IllegalStateException("JWT_SECRET_KEY deve ter pelo menos 32 bytes.");
            return Keys.hmacShaKeyFor(rawBytes);
        }
    }

    public static String generateToken(String userId, String name, String email) {
        return Jwts.builder() //
                       .subject(email) //
                       .issuedAt(new Date()) //
                       .id(userId) //
                       .claim("name", name) //
                       .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6)) // 6 horas
                       .signWith(SECRET_KEY, SignatureAlgorithm.HS256) //
                       .compact();
    }

    public static boolean isJwtValid(String token) {
        try {
            if (token == null || !token.startsWith("Bearer")) return false;
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token.substring(7)).getPayload();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
