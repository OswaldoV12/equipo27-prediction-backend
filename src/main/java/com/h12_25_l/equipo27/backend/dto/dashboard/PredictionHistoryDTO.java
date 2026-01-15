package com.h12_25_l.equipo27.backend.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Registro histórico de una predicción realizada para un vuelo")
public record PredictionHistoryDTO(

        @Schema(
                description = "Identificador del vuelo asociado a la predicción",
                example = "42"
        )
        Long vueloId,

        @Schema(
                description = "Fecha y hora en que se generó la predicción (sin zona horaria)",
                type = "string",
                example = "2026-01-03T10:15:30"
        )
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        @Schema(
                description = "Resultado final de la predicción",
                example = "Retrasado"
        )
        TipoPrevision prevision,

        @Schema(
                description = "Probabilidad asociada a la predicción",
                example = "0.87"
        )
        Double probabilidad
) {}
