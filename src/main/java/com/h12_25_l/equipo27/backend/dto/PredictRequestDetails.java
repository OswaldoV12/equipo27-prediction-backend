package com.h12_25_l.equipo27.backend.dto;

import com.h12_25_l.equipo27.backend.model.PredictionRequest;

import java.time.LocalDateTime;

public record PredictRequestDetails(Long id,
                                    String aerolinea,
                                    String origen,
                                    String destino,
                                    LocalDateTime fechaPartida,
                                    Integer distanciaKm) {
    public PredictRequestDetails(PredictionRequest request){
        this(
                request.getId(),
                request.getAerolinea(),
                request.getOrigen(),
                request.getDestino(),
                request.getFechaPartida(),
                request.getDistanciaKm()
        );
    }
}
