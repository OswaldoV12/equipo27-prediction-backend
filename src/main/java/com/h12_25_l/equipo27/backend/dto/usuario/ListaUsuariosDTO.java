package com.h12_25_l.equipo27.backend.dto.usuario;

import com.h12_25_l.equipo27.backend.enums.Roles;

public record ListaUsuariosDTO(
        Long idUsuario,
        String username,
        String email,
        Roles rol
) {
}
