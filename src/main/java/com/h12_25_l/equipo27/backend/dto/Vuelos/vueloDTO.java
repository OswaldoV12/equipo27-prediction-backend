package com.h12_25_l.equipo27.backend.dto.Vuelos;

import java.time.LocalDateTime;

public record vueloDTO(
        String origen,
        String destino,
        LocalDateTime fecha
) {
}
