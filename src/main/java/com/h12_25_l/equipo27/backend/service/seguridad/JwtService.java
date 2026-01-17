package com.h12_25_l.equipo27.backend.service.seguridad;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.h12_25_l.equipo27.backend.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    // Clave secreta para firmar el token (puede ir en application.properties)
    private static final String SECRET_KEY = "mi-clave-super-secreta-para-auth0";

    // Algoritmo con la clave
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    // Generar token
    public String generateToken(Usuario user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("rol", user.getRol().name())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .sign(algorithm); // firmar token
    }

    // Obtener username(email) desde el token
    public String getUsernameFromToken(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }

    // Validar token
    public boolean isTokenValid(String token, String username) {
        String usernameFromToken = getUsernameFromToken(token);
        return username.equals(usernameFromToken);
    }
}
