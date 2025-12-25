package com.h12_25_l.equipo27.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record PredictRequestDTO(

        @NotBlank(message = "La aerolínea es obligatoria")
        @Pattern(
                regexp = "^[A-Z]{2,3}$",
                message = "La aerolínea debe tener 2 o 3 letras mayúsculas (IATA)"
        )
        String aerolinea,

        @NotBlank(message = "El aeropuerto de origen es obligatorio")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "El código IATA de origen debe tener 3 letras mayúsculas"
        )
        String origen,

        @NotBlank(message = "El aeropuerto de destino es obligatorio")
        @Pattern(
                regexp = "^[A-Z]{3}$",
                message = "El código IATA de destino debe tener 3 letras mayúsculas"
        )
        String destino,

        @NotNull(message = "La fecha de partida es obligatoria")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime fechaPartida,

        @NotNull(message = "La distancia es obligatoria")
        @Positive(message = "La distancia debe ser mayor a 0")
        Integer distanciaKm
) {}

