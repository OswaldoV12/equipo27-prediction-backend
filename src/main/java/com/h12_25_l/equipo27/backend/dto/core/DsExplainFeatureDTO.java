package com.h12_25_l.equipo27.backend.dto.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Feature relevante en la explicabilidad del modelo DS")
public record DsExplainFeatureDTO(

        @Schema(example = "aerolinea_WN <= 0.00")
        String feature,

        @Schema(example = "-0.2979")
        Double weight,

        @Schema(example = "en contra del retraso")
        String direction
) {}
