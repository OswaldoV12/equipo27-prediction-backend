package com.h12_25_l.equipo27.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PredictionHistoryDTO(
        Long vueloId,                        // nuevo campo
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,
        String prevision,
        Double probabilidad
) {}
