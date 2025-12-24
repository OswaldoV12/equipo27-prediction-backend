package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.PredictResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {
    private final DsApiClient dsApiClient;

    public PredictionService(DsApiClient dsApiClient) {
        this.dsApiClient = dsApiClient;
    }

    public PredictResponseDTO predict(PredictRequestDTO request) {

        // Mapear request a formato DS
        // De momento
        Object dsRequest = request;

        // Llamamos a API de DS
        PredictResponseDTO response = dsApiClient.predict(dsRequest);

        // Nomas para validar que no sea nulo
        if (response == null) {
            throw new RuntimeException("Respuesta inválida del modelo DS");
        }

        // Devolvemos el resultado :)
        return response;
    }
}
