package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Aerolínea disponible en el sistema")
public record AerolineaDTO (

        @Schema(
                description = "Código IATA de la aerolinea",
                example = "AV"
        )
        String iata,

        @Schema(
                description = "Nombre comercial de la aerolínea",
                example = "Avianca"
        )
        String nombre){
}
