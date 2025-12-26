package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.dto.PredictResponseDTO;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DsApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DsApiClient.class);

    private final WebClient webClient;

    public DsApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public PredictResponseDTO predict(Object dsRequest) {
        try {
            return webClient.post()
                    .uri("/predict")
                    .bodyValue(dsRequest)
                    .retrieve()
                    .bodyToMono(PredictResponseDTO.class)
                    .block();
        } catch (Exception e) {
            LOG.error("Error al llamar al servicio DS", e);
            throw new ExternalServiceException(
                    "Error de comunicación con servicio DS",
                    e
            );

        }
    }
}



