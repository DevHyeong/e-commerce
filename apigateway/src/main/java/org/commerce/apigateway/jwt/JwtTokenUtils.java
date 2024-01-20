package org.commerce.apigateway.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenUtils {
    public static boolean isValid(String token, String secretKey){
        log.info("jwt token: {}, secret key: {}", token, secretKey);
        try{
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
            return true;
        }catch(Exception e){
            log.error("", e);
            return false;
        }
    }
    public static String extractSubject(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
