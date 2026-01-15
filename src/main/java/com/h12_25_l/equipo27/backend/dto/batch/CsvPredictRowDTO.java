package com.h12_25_l.equipo27.backend.dto.batch;

public record CsvPredictRowDTO(
        String aerolinea,
        String origen,
        String destino,
        String fecha_partida,
        String distancia_km
) {}