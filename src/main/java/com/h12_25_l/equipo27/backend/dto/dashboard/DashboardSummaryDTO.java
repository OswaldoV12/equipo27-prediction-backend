package com.h12_25_l.equipo27.backend.dto.dashboard;

import io.swagger.v3.oas.annotations.media.Schema;

// DTO para el resumen de predicciones (totales y porcentajes)
@Schema(description = "Resumen estadístico de las predicciones realizadas")
public record DashboardSummaryDTO(

        @Schema(
                description = "Número total de predicciones registradas",
                example = "150"
        )
        long totalPredicciones,

        @Schema(
                description = "Cantidad de vuelos que salieron a tiempo",
                example = "95"
        )
        long puntuales,

        @Schema(
                description = "Cantidad de vuelos con retraso",
                example = "55"
        )
        long retrasos,

        @Schema(
                description = "Porcentaje de vuelos puntuales",
                example = "63.33"
        )
        double porcentajePuntuales,

        @Schema(
                description = "Porcentaje de vuelos con retraso",
                example = "36.67"
        )
        double porcentajeRetrasos
) {}
