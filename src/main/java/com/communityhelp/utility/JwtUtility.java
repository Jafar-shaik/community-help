package com.communityhelp.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtility {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    public SecretKey getSingingKey(){
        byte[] decode = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decode);
    }

    public String generateToken(String username, Map<String , Claims> extraClaims){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSingingKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateToken(String username){
        return generateToken(username,Map.of());
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSingingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T>T extractClaim(String token , java.util.function.Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username){
        try{
            String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && ! isTokenExpired(token));
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}

