package com.coral.util;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class TokenUtil {

    private static final String SECRET_KEY_BASE64 = 
        "c300bWFyU3VwZXJTZWNyZXRrZXkzMjFVUEVSbWFnYUF1dGhvcmg="; 
    
    private static final long EXPIRATION_TIME = TimeUnit.HOURS.toMillis(4); 

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Gera um JWT para um dado email (identidade).
     * @param email O email do Membro que será o "subject" do token.
     * @return O JWT gerado.
     */
    public static String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(email) 
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrai os Claims (cargas) do token.
     * @param token O JWT a ser analisado.
     * @return O objeto Claims contendo todas as informações do token.
     */
    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrai o email (subject) do token.
     * @param token O JWT a ser analisado.
     * @return O email do usuário.
     */
    public static String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Valida o token (verifica assinatura e expiração).
     * @param token O JWT a ser validado.
     * @return true se o token for válido e não expirado, false caso contrário.
     */
    public static boolean validateToken(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (Exception e) {
            System.err.println("Erro de validação JWT: " + e.getMessage());
            return false;
        }
    }
}