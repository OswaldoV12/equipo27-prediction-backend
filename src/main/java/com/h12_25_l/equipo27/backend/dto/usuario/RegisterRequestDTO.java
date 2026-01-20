package com.h12_25_l.equipo27.backend.dto.usuario;

public record RegisterRequestDTO(
        String username,
        String email,
        String password
) {
}
