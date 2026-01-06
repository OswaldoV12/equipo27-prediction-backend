package com.h12_25_l.equipo27.backend.dto.externalapi;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos meteorológicos del aeropuerto en la hora de partida")
public record WeatherDataDTO(

        @Schema(
                description = "Temperatura en °C a 2 metros de altura",
                example = "25.3"
        )
        Double temperature2m,

        @Schema(
                description = "Velocidad del viento a 10 metros de altura en km/h",
                example = "12.1"
        )
        Double windSpeed10m,

        @Schema(
                description = "Visibilidad en kilómetros",
                example = "10.0"
        )
        Double visibility
) {}
