package com.footalentgroup.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    @Value("${jwt.secret}")
    private String jwt_secret;

    @Value("${jwt.expiration}")
    private Long jwt_expiration;

    /**
     * Genera un token JWT para el usuario autenticado.
     * El token incluye el nombre de usuario, la fecha de emisión y una fecha de expiración.
     * También se firma con una clave .
     *
     * @param authentication Objeto de autenticación que contiene los datos del usuario autenticado.
     * @return El token JWT generado como cadena de texto.
     */

    public String generateToken(Authentication authentication) {
        UserDetails mainUser= (UserDetails) authentication.getPrincipal();
        SecretKey key = Keys.hmacShaKeyFor(jwt_secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder().setSubject(mainUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwt_expiration * 1000L))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    //compara el username (email) y comprobando si ha expirado.
    public Boolean validateToken(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //determina si el token ha expirado
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //extrae la fecha de expiracion del token
    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    //se extraen los datos del token
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
