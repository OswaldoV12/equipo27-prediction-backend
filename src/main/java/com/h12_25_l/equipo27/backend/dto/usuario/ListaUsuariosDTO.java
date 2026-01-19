package com.h12_25_l.equipo27.backend.dto.usuario;

import com.h12_25_l.equipo27.backend.enums.Roles;

public record ListaUsuariosDTO(
        long idUser,
        String username,
        String email,
        Roles role
) {
}
