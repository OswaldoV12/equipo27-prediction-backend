package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request para calcular distancia entre dos aeropuertos")
public record DistanciaRequestDTO(
        @Schema(description = "Coordenadas del aeropuerto de origen")
        CoordenadasDTO origen,

        @Schema(description = "Coordenadas del aeropuerto de destino")
        CoordenadasDTO destino
) {}
