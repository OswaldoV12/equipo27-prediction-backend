package com.h12_25_l.equipo27.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

// DTO para el resumen de predicciones (totales y porcentajes)
public record DashboardSummaryDTO(
        long totalPredicciones,
        long puntuales,
        long retrasos,
        double porcentajePuntuales,
        double porcentajeRetrasos
) {}
