package com.h12_25_l.equipo27.backend.dto.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Explicabilidad del modelo DS")
public record DsExplicabilidadDTO(

        @Schema(example = "LIME")
        String metodo,

        List<DsExplainFeatureDTO> top_3_features
) {

    /** JSON para persistencia */
    public String toJson(ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /** Texto simple para frontend */
    public String toHumanReadable() {
        if (top_3_features == null || top_3_features.isEmpty()) {
            return "No hay factores relevantes para la predicción";
        }

        StringBuilder sb = new StringBuilder("Factores principales: ");
        top_3_features.forEach(f ->
                sb.append(f.feature())
                        .append(" (")
                        .append(f.direction())
                        .append(", peso ")
                        .append(String.format("%.2f", f.weight()))
                        .append("); ")
        );
        return sb.toString();
    }
}
