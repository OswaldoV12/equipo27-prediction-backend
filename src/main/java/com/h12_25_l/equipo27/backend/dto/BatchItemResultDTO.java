package com.h12_25_l.equipo27.backend.dto;

import com.h12_25_l.equipo27.backend.dto.PredictResponseDTO;

public record BatchItemResultDTO(
        CsvPredictRowDTO input,
        PredictResponseDTO output,
        String error
) {}