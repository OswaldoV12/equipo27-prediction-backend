package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.dto.PredictResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DsApiClient {

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
            throw new RuntimeException("Error al consultar el servicio DS");
        }
    }
}
