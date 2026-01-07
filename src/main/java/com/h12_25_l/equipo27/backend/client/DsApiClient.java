package com.h12_25_l.equipo27.backend.client;

import com.h12_25_l.equipo27.backend.dto.core.DsPredictResponseDTO;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.entity.DsMetrics;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import com.h12_25_l.equipo27.backend.repository.DsMetricsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class DsApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DsApiClient.class);

    private final WebClient webClient;
    private final DsMetricsRepository metricsRepository;

    public DsApiClient(WebClient webClient, DsMetricsRepository metricsRepository) {
        this.webClient = webClient;
        this.metricsRepository = metricsRepository;
    }

    /**
     * Devuelve el DTO completo de DS, incluyendo latencia y explicabilidad.
     * Este método actualiza métricas globales en BD.
     */
    public DsPredictResponseDTO predictRaw(Object dsRequest) {
        // Obtener registro global o crear si no existe
        DsMetrics metrics = metricsRepository.findById(1L)
                .orElseGet(() -> metricsRepository.save(new DsMetrics()));

        try {
            DsPredictResponseDTO dsResponse = webClient.post()
                    .uri("/predict")
                    .bodyValue(dsRequest)
                    .retrieve()
                    .bodyToMono(DsPredictResponseDTO.class)
                    .block();

            if (dsResponse == null) {
                metrics.incrementFailure();
                metricsRepository.save(metrics);
                throw new ExternalServiceException("Respuesta nula desde DS");
            }

            // Request exitosa
            metrics.incrementSuccess();
            metricsRepository.save(metrics);

            return dsResponse;

        } catch (Exception e) {
            // Request fallida
            metrics.incrementFailure();
            metricsRepository.save(metrics);

            LOG.error("Error al llamar al servicio DS", e);
            throw new ExternalServiceException(
                    "Error de comunicación con servicio DS",
                    e
            );
        }
    }

    /**
     * Devuelve solo los campos que van al frontend (opcional)
     */
    public DsPredictResponseDTO predict(Object dsRequest) {
        return predictRaw(dsRequest);
    }

    /**
     * Convierte la string prevision de DS a TipoPrevision
     */
    public static TipoPrevision mapPrevisionFromDs(String dsValue) {
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
