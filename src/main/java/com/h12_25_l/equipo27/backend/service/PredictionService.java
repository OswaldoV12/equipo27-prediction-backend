package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.PredictResponseDTO;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    private static final Logger LOG = LoggerFactory.getLogger(PredictionService.class);

    private final DsApiClient dsApiClient;

    public PredictionService(DsApiClient dsApiClient) {
        this.dsApiClient = dsApiClient;
    }

    public PredictResponseDTO predict(PredictRequestDTO request) {

        // Regla de negocio REAL (ejemplo válido)
        if (request.origen().equals(request.destino())) {
            LOG.warn("Origen y destino iguales: {}", request.origen());
            throw new ValidationException("Origen y destino no pueden ser iguales");

        }

        PredictResponseDTO response = dsApiClient.predict(request);

        if (response == null) {
            LOG.warn("Respuesta nula del modelo DS");
            throw new ExternalServiceException("Respuesta inválida del modelo DS");

        }

        return response;
    }
}



