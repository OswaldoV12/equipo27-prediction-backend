package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta del servicio de Data Science con la predicción del vuelo")
public record DsPredictResponseDTO(

        @Schema(
                description = "Resultado de la predicción generado por el modelo",
                example = "retrasado",
                allowableValues = {"retrasado", "no retrasado"}
        )
        String prevision,

        @Schema(
                description = "Probabilidad asociada a la predicción (valor entre 0 y 1)",
                example = "0.82"
        )
        Double probabilidad,

        @Schema(
                description = "Tiempo de respuesta del modelo DS en milisegundos",
                example = "2.12"
        )
        Double latencia_ms,

        @Schema(
                description = "Explicación textual del motivo de la predicción",
                example = "La aerolínea presenta retrasos recurrentes en esta franja horaria"
        )
        DsExplicabilidadDTO explicabilidad
) {}

