package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aeropuertos disponibles en el sistema")
public record AeropuertoDTO(
        @Schema(description = "Código IATA del aeropuerto", example = "SJO")
        String iata,

        @Schema(description = "Nombre del aeropuerto", example = "Juan Santamaría")
        String nombre,

        @Schema(description = "Latitud del aeropuerto", example = "9.9937")
        Double latitud,

        @Schema(description = "Longitud del aeropuerto", example = "-84.2088")
        Double longitud
) {
}
