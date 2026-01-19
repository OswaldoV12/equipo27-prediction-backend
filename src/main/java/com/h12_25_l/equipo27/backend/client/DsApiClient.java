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

    // Single-request: soporta explain=true
    public DsPredictResponseDTO[] predictRaw(Object dsRequest, boolean explain) {
        DsMetrics metrics = metricsRepository.findById(1L)
                .orElseGet(() -> metricsRepository.save(new DsMetrics()));

        try {
            LOG.info("Llamando al modelo DS...");
            String uri = "/predict" + (explain ? "?explain=true" : "");

            // Envía SIEMPRE como array
            DsPredictResponseDTO[] dsResponseArray = webClient.post()
                    .uri(uri)
                    .bodyValue(List.of(dsRequest))
                    .retrieve()
                    .bodyToMono(DsPredictResponseDTO[].class)
                    .block();

            if (dsResponseArray == null || dsResponseArray.length == 0) {
                metrics.incrementFailure();
                metricsRepository.save(metrics);
                throw new ExternalServiceException("Respuesta nula o vacía desde DS");
            }

            metrics.incrementSuccess();
            metricsRepository.save(metrics);
            LOG.info("Predicción DS recibida. Total elementos: {}", dsResponseArray.length);

            return dsResponseArray;

        } catch (Exception e) {
            metrics.incrementFailure();
            metricsRepository.save(metrics);
            LOG.error("Error comunicándose con DS", e);
            throw new ExternalServiceException("Error de comunicación con servicio DS", e);
        }
    }


    // Batch-request: explicabilidad siempre null
    public DsPredictResponseDTO[] predictRawArray(List<?> dsRequestList) {
        DsMetrics metrics = metricsRepository.findById(1L)
                .orElseGet(() -> metricsRepository.save(new DsMetrics()));

        try {
            LOG.info("Llamando al modelo DS con array...");
            DsPredictResponseDTO[] dsResponseArray = webClient.post()
                    .uri("/predict")
                    .bodyValue(dsRequestList)
                    .retrieve()
                    .bodyToMono(DsPredictResponseDTO[].class)
                    .block();

            if (dsResponseArray == null || dsResponseArray.length == 0) {
                metrics.incrementFailure();
                metricsRepository.save(metrics);
                throw new ExternalServiceException("Respuesta nula o vacía desde DS");
            }

            metrics.incrementSuccess();
            metricsRepository.save(metrics);
            LOG.info("Predicción DS recibida. Total elementos: {}", dsResponseArray.length);

            return dsResponseArray;

        } catch (Exception e) {
            metrics.incrementFailure();
            metricsRepository.save(metrics);
            LOG.error("Error comunicándose con DS", e);
            throw new ExternalServiceException("Error de comunicación con DS", e);
        }
    }

    public static TipoPrevision mapPrevisionFromDs(String dsValue) {
        if (dsValue == null) throw new ExternalServiceException("Previsión nula desde DS");
        return switch (dsValue.toLowerCase()) {
            case "retrasado" -> TipoPrevision.Retrasado;
            case "no retrasado" -> TipoPrevision.No_Retrasado;
            default -> throw new ExternalServiceException("Previsión desconocida desde DS: " + dsValue);
        };
    }
}


