package com.h12_25_l.equipo27.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PredictRequestDTO(
        @NotBlank(message = "La aerolínea es obligatoria")
        @Size(min = 2, max = 3, message = "La aerolínea debe tener entre 2 y 3 caracteres")
        String aerolinea,

        @NotBlank(message = "El aeropuerto de origen es obligatorio")
        @Size(min = 3, max = 3, message = "El código IATA de origen debe tener 3 letras")
        String origen,

        @NotBlank(message = "El aeropuerto de destino es obligatorio")
        @Size(min = 3, max = 3, message = "El código IATA de destino debe tener 3 letras")
        String destino,

        @NotNull(message = "La fecha de partida es obligatoria")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime fechaPartida,

        @NotNull(message = "La distancia es obligatoria")
        @Positive(message = "La distancia debe ser mayor a 0")
        Integer distanciaKm
) {}
