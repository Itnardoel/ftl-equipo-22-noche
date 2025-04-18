package com.footalentgroup.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Profile("!test")
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwt_secret;

    @Value("${jwt.expiration}")
    private Long jwt_expiration;

    public String generateToken(Authentication authentication) {
        UserDetails mainUser= (UserDetails) authentication.getPrincipal();
        SecretKey key = Keys.hmacShaKeyFor(jwt_secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().setSubject(mainUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwt_expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public Claims extractAllClaims(String token){
        SecretKey key = Keys.hmacShaKeyFor(jwt_secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUserName(String token){
        return extractAllClaims(token).getSubject();
    }
}
