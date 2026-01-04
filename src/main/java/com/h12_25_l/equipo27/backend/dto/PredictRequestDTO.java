package com.h12_25_l.equipo27.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "Solicitud de predicción de retraso de vuelo")
public record PredictRequestDTO(

        @Schema(
                description = "Código IATA de la aerolínea (2 o 3 letras mayúsculas)",
                example = "AV"
        )
        @NotBlank(message = "La aerolínea es obligatoria")
        @Pattern(
                regexp = "^[A-Z]{2,3}$",
                message = "La aerolínea debe tener 2 o 3 letras mayúsculas (IATA)"
        )
        String aerolinea,

        @Schema(
                description = "Código IATA del aeropuerto de origen",
                example = "SJO"
        )
        @NotBlank(message = "El aeropuerto de origen es obligatorio")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "El código IATA de origen debe tener 3 letras mayúsculas"
        )
        String origen,

        @Schema(
                description = "Código IATA del aeropuerto de destino",
                example = "SYQ"
        )
        @NotBlank(message = "El aeropuerto de destino es obligatorio")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "El código IATA de destino debe tener 3 letras mayúsculas"
        )
        String destino,

        @Schema(
                description = "Fecha y hora local de salida del vuelo (sin zona horaria)",
                type = "string",
                example = "2025-12-25T10:30:00"
        )
        @NotNull(message = "La fecha de partida es obligatoria")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime fecha_partida,

        @Schema(
                description = "Distancia del vuelo en kilómetros",
                example = "650"
        )
        @NotNull(message = "La distancia es obligatoria")
        @Positive(message = "La distancia debe ser mayor a 0")
        Integer distancia_km
) {}
