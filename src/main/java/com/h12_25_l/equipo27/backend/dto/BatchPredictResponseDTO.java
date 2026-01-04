package com.h12_25_l.equipo27.backend.dto;

import java.util.List;

public record BatchPredictResponseDTO(
        int totalFilas,
        int procesadasOk,
        int conError,
        List<BatchItemResultDTO> resultados
) {}