package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aeropuertos disponibles en el sistema")
public record AeropuertoDTO(
        @Schema(
                description = "Código IATA del aeropuerto",
                example = "SJO"
        )
        String iata,

        @Schema(
                description = "Nombre del aeropuerto",
                example = "Juan Santamaría"
        )
        String nombre
) {
}
