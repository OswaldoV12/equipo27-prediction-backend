package com.h12_25_l.equipo27.backend.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;

// DTO recibido desde el servicio de Data Science
@Schema(description = "Respuesta del servicio de Data Science con la predicción del vuelo")
public record DsPredictResponseDTO(

        @Schema(
                description = "Resultado de la predicción generado por el modelo",
                example = "Retrasado",
                allowableValues = {"retrasado", "no retrasado"}
        )
        String prevision,

        @Schema(
                description = "Probabilidad asociada a la predicción (valor entre 0 y 1)",
                example = "0.82"
        )
        Double probabilidad
) {}
