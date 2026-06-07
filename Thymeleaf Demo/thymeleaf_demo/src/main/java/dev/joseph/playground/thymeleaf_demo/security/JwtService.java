package dev.joseph.playground.thymeleaf_demo.security;


import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;
    
    public String createToken(UserDetails userDetails, Map<String, Object> claims){
        return Jwts
                .builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(convertToKey(secretKey), Jwts.SIG.HS256)
                .subject(userDetails.getUsername())
                .claims(claims)
                .compact();
    }
    
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(userDetails, claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts
            .parser()
            .verifyWith(convertToKey(secretKey))
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }


    public  <T> T extractClaim(String token , Function<Claims, T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUserName(String token){
        Claims claim = extractAllClaims(token);
        String userName = claim.getSubject();
        return userName;
    }

    public Date extractExpiration(String token){
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token){
        Date expiration = extractExpiration(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String userName = userDetails.getUsername();
        String claimUserName = extractUserName(token);
        return (userName.equals(claimUserName) && !isTokenExpired(token));
    }

    private SecretKey convertToKey(String plainString){
        byte[] decodeKey = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(decodeKey);
        SecretKey secretKey = new SecretKeySpec(decodeKey, "HmacSHA256");
        return secretKey;
    }
}
