package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Distancia calculada entre dos puntos")
public record DistanciaDTO(
        @Schema(description = "Distancia en kilómetros", example = "980.5")
        double distanciaKm
) {}
