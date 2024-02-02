package org.commerce.user.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TokenUtils {
    public static String generateToken(String secretKey, Long userId, LocalDateTime expiredAt){
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(convert(expiredAt))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private static Date convert(LocalDateTime dateTime){
        Instant instant = dateTime
                .atZone(ZoneId.of("Asia/Seoul"))
                .toInstant();
        return Date.from(instant);
    }
}
