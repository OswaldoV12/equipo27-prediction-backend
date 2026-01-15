package com.h12_25_l.equipo27.backend.dto.core;

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
        Double probabilidad,

        @Schema(
                description = "Tiempo de respuesta del modelo DS",
                example = "2.12"
        )
                Double latencia,

        @Schema(
                description = "Descipcion del motivo asociado al retrazo de un vuelo",
                example = "La aerolinea AZ tiene retrazos concurrentes a la hora 14:30:00"
        )
        String explicabilidad
) {}
