package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Coordenadas de un aeropuerto")
public record CoordenadasDTO(
        @Schema(description = "Latitud del aeropuerto", example = "33.6407")
        double latitud,

        @Schema(description = "Longitud del aeropuerto", example = "-84.4277")
        double longitud
) {}
