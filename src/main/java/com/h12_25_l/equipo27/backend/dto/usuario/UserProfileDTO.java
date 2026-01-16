package com.h12_25_l.equipo27.backend.dto.usuario;

import com.h12_25_l.equipo27.backend.enums.Roles;

public record UserProfileDTO(
        String username,
        String email,
        Roles role
) {
}
