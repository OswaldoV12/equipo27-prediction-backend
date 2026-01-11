package com.h12_25_l.equipo27.backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.h12_25_l.equipo27.backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private static final String SECRET_KEY = "123456";

    public String generarToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withSubject(user.getEmail())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException("Error al generar el token JWT: ", e);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException e){
            throw new RuntimeException("Token JWT expirado o invalido: ", e);
        }
    }
}
