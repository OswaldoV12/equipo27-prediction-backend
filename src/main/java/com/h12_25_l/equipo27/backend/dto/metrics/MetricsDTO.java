package com.h12_25_l.equipo27.backend.dto.metrics;

import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Resumen de predicciones con datos de vuelo y métricas DS")
public record MetricsDTO(

        @Schema(description = "Código IATA de la aerolinea")
        String aerolineaIata,

        @Schema(description = "Nombre de la aerolinea")
        String aerolineaNombre,

        @Schema(description = "Código IATA del aeropuerto de origen")
        String origenIata,

        @Schema(description = "Nombre del aeropuerto de origen")
        String origenNombre,

        @Schema(description = "Código IATA del aeropuerto de destino")
        String destinoIata,

        @Schema(description = "Nombre del aeropuerto de destino")
        String destinoNombre,

        @Schema(description = "Fecha de partida del vuelo")
        LocalDateTime fechaPartida,

        @Schema(description = "Distancia en km del vuelo")
        Integer distanciaKm,

        @Schema(description = "Predicción de retraso")
        TipoPrevision prevision,

        @Schema(description = "Probabilidad asociada a la predicción")
        Double probabilidad,

        @Schema(description = "Latencia del modelo DS")
        Double latencia,

        @Schema(description = "Explicabilidad del modelo DS")
        String explicabilidad,

        @Schema(description = "Fecha de creación de la predicción")
        LocalDateTime createdAt
) {}
