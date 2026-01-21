package com.h12_25_l.equipo27.backend.client;

import com.h12_25_l.equipo27.backend.dto.core.DsPredictResponseDTO;
import com.h12_25_l.equipo27.backend.entity.DsMetrics;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import com.h12_25_l.equipo27.backend.repository.DsMetricsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Component
public class DsApiClient {

    private static final Logger LOG = LoggerFactory.getLogger(DsApiClient.class);
    private final WebClient webClient;
    private final DsMetricsRepository metricsRepository;

    public DsApiClient(WebClient webClient, DsMetricsRepository metricsRepository) {
        this.webClient = webClient;
        this.metricsRepository = metricsRepository;
    }

    // =========================
    // Single request (explain)
    // =========================
    public DsPredictResponseDTO[] predictRaw(Object dsRequest, boolean explain) {

        DsMetrics metrics = metricsRepository.findById(1L)
                .orElseGet(() -> metricsRepository.save(new DsMetrics()));

        try {
            LOG.info("Llamando al modelo DS...");
            String uri = "/predict" + (explain ? "?explain=true" : "");

            DsPredictResponseDTO[] response = webClient.post()
                    .uri(uri)
                    .bodyValue(List.of(dsRequest))     // SIEMPRE array
                    .retrieve()
                    .bodyToMono(DsPredictResponseDTO[].class)
                    .timeout(Duration.ofSeconds(5))   // ⏱️ CLAVE
                    .block();

            if (response == null || response.length == 0) {
                throw new ExternalServiceException("Respuesta vacía desde DS");
            }

            metrics.incrementSuccess();
            metricsRepository.save(metrics);

            return response;

        } catch (Exception e) {
            metrics.incrementFailure();
            metricsRepository.save(metrics);

            LOG.error("Error comunicándose con DS", e);
            throw new ExternalServiceException(
                    "Servicio DS no disponible o timeout", e
            );
        }
    }

    // =========================
    // Batch request
    // =========================
    public DsPredictResponseDTO[] predictRawArray(List<?> dsRequestList) {

        DsMetrics metrics = metricsRepository.findById(1L)
                .orElseGet(() -> metricsRepository.save(new DsMetrics()));

        try {
            LOG.info("Llamando al modelo DS (batch)...");

            DsPredictResponseDTO[] response = webClient.post()
                    .uri("/predict")
                    .bodyValue(dsRequestList)
                    .retrieve()
                    .bodyToMono(DsPredictResponseDTO[].class)
                    .timeout(Duration.ofSeconds(5))   // ⏱️ CLAVE
                    .block();

            if (response == null || response.length == 0) {
                throw new ExternalServiceException("Respuesta vacía desde DS");
            }

            metrics.incrementSuccess();
            metricsRepository.save(metrics);

            return response;

        } catch (Exception e) {
            metrics.incrementFailure();
            metricsRepository.save(metrics);

            LOG.error("Error comunicándose con DS", e);
            throw new ExternalServiceException(
                    "Servicio DS no disponible o timeout", e
            );
        }
    }

    // =========================
    // Mapper
    // =========================
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
