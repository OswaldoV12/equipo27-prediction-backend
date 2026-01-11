package com.h12_25_l.equipo27.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private static final String SECRET_KEY = "123456";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    // Generar token
    public String generateToken(String email) {
        try {
            return JWT.create()
                    .withSubject(email)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException("Error al generar el token JWT: ", e);
        }
    }

    // Obtener username(email) desde el token
    public String getUsernameFromToken(String tokenJWT){
        try {
            return JWT.require(algorithm)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new RuntimeException("Token JWT expirado o invalido: ", e);
        }
    }

    // Validar token
    public boolean isTokenValid(String token, String username) {
        String usernameFromToken = getUsernameFromToken(token);
        return username.equals(usernameFromToken);
    }
}
