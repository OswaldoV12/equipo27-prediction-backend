package com.h12_25_l.equipo27.backend.dto.core;

import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import io.swagger.v3.oas.annotations.media.Schema;

// DTO que se envía al frontend con el resultado de la predicción
@Schema(description = "Resultado de la predicción de retraso del vuelo")
public record PredictResponseDTO(

        @Schema(
                description = "Resultado de la predicción del vuelo",
                example = "Retrasado"
        )
        TipoPrevision prevision,

        @Schema(
                description = "Probabilidad asociada a la predicción en porcentaje",
                example = "74"
        )
        Double probabilidad

) {}
