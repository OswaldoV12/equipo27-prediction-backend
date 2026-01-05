package com.h12_25_l.equipo27.backend.client;

import com.h12_25_l.equipo27.backend.dto.dashboard.DsPredictResponseDTO;
import com.h12_25_l.equipo27.backend.dto.core.PredictResponseDTO;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DsApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DsApiClient.class);

    private final WebClient webClient;

    public DsApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public PredictResponseDTO predict(Object dsRequest) {

        try {
            DsPredictResponseDTO dsResponse = webClient.post()
                    .uri("/predict")
                    .bodyValue(dsRequest)
                    .retrieve()
                    .bodyToMono(DsPredictResponseDTO.class)
                    .block();

            if (dsResponse == null) {
                throw new ExternalServiceException("Respuesta nula desde DS");
            }

            // 🔑 Mapeo DS → Dominio
            return new PredictResponseDTO(
                    mapPrevisionFromDs(dsResponse.prevision()),
                    dsResponse.probabilidad()
            );

        } catch (Exception e) {
            LOG.error("Error al llamar al servicio DS", e);
            throw new ExternalServiceException(
                    "Error de comunicación con servicio DS",
                    e
            );
        }
    }

    private TipoPrevision mapPrevisionFromDs(String dsValue) {

        if (dsValue == null) {
            throw new ExternalServiceException("Previsión nula desde DS");
        }

        return switch (dsValue.toLowerCase()) {
            case "retrasado" -> TipoPrevision.Retrasado;
            case "no retrasado" -> TipoPrevision.No_Retrasado;
            default -> throw new ExternalServiceException(
                    "Previsión desconocida desde DS: " + dsValue
            );
        };
    }
}

