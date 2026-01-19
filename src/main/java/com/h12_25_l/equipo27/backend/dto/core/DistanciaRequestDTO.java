package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request para calcular distancia entre dos aeropuertos")
public record DistanciaRequestDTO(

        @NotNull
        @Valid
        @Schema(description = "Coordenadas del aeropuerto de origen")
        CoordenadasDTO origen,

        @NotNull
        @Valid
        @Schema(description = "Coordenadas del aeropuerto de destino")
        CoordenadasDTO destino
) {}
