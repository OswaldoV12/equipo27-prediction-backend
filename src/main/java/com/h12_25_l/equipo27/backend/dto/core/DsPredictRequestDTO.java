package com.h12_25_l.equipo27.backend.dto.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "Solicitud unificada de predicción para el modelo DS")
public record DsPredictRequestDTO(

        @Schema(
                description = "Código IATA de la aerolínea (2 o 3 letras mayúsculas)",
                example = "AV"
        )
        @NotBlank
        @Pattern(regexp = "^[A-Z]{2,3}$")
        String aerolinea,

        @Schema(
                description = "Código IATA del aeropuerto de origen",
                example = "SJO"
        )
        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$")
        String origen,

        @Schema(
                description = "Código IATA del aeropuerto de destino",
                example = "SYQ"
        )
        @NotBlank
        @Pattern(regexp = "^[A-Z]{3}$")
        String destino,

        @Schema(
                description = "Fecha y hora local de salida del vuelo (sin zona horaria)",
                type = "string",
                example = "2025-12-25T10:30:00"
        )
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime fecha_partida,

        @Schema(
                description = "Distancia del vuelo en kilómetros",
                example = "650"
        )
        @NotNull
        @Positive
        Integer distancia_km,

        @Schema(description = "Temperatura en °C a 2 metros de altura", example = "25.3")
        Double temperature2m,

        @Schema(description = "Velocidad del viento a 10 metros de altura en km/h", example = "12.1")
        Double windSpeed10m,

        @Schema(description = "Visibilidad en kilómetros", example = "10.0")
        Double visibility
) {}
