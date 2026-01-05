package com.h12_25_l.equipo27.backend.dto.batch;

import com.h12_25_l.equipo27.backend.dto.core.PredictResponseDTO;

public record BatchItemResultDTO(
        CsvPredictRowDTO input,
        PredictResponseDTO output,
        String error
) {}