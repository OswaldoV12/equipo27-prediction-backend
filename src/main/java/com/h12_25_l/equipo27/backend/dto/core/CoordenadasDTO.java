package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Coordenadas de un aeropuerto")
public record CoordenadasDTO(

        @NotNull
        @DecimalMin(value = "-90.0", message = "Latitud mínima es -90")
        @DecimalMax(value = "90.0", message = "Latitud máxima es 90")
        @Schema(description = "Latitud del aeropuerto", example = "33.6407")
        Double latitud,

        @NotNull
        @DecimalMin(value = "-180.0", message = "Longitud mínima es -180")
        @DecimalMax(value = "180.0", message = "Longitud máxima es 180")
        @Schema(description = "Longitud del aeropuerto", example = "-84.4277")
        Double longitud
) {}
