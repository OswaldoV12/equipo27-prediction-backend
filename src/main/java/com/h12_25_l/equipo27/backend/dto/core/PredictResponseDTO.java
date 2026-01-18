package com.h12_25_l.equipo27.backend.dto.core;

import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado de la predicción de retraso del vuelo")
public record PredictResponseDTO(

        @Schema(
                description = "Resultado de la predicción del vuelo",
                example = "RETRASADO"
        )
        TipoPrevision prevision,

        @Schema(
                description = "Probabilidad asociada a la predicción",
                example = "0.74"
        )
        Double probabilidad,

        @Schema(
                description = "Explicación textual simplificada para el frontend",
                example = "Alta congestión en el aeropuerto de origen"
        )
        String explicabilidad
) {}
