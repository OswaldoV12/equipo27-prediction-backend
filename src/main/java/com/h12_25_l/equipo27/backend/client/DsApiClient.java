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

    public DsApiClient(WebClient webClient,
                       DsMetricsRepository metricsRepository) {
        this.webClient = webClient;
        this.metricsRepository = metricsRepository;
    }

    /**
     * Llama al modelo DS con un único objeto y devuelve la respuesta completa.
     * Actualiza métricas globales.
     */
    public DsPredictResponseDTO predictRaw(Object dsRequest) {

        DsMetrics metrics = metricsRepository.findById(1L)
                .orElseGet(() -> metricsRepository.save(new DsMetrics()));

        try {
            LOG.info("Llamando al modelo DS...");

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

            metrics.incrementSuccess();
            metricsRepository.save(metrics);

            LOG.info("Predicción DS: {}", dsResponse.prevision());

            return dsResponse;

        } catch (Exception e) {
            metrics.incrementFailure();
            metricsRepository.save(metrics);

            LOG.error("Error comunicándose con DS", e);
            throw new ExternalServiceException(
                    "Error de comunicación con servicio DS",
                    e
            );
        }
    }

    /**
     * Llama al modelo DS enviando un array/lista de requests.
     * Devuelve un array de respuestas.
     */
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

    /**
     * Devuelve solo los campos que van al frontend (opcional)
     */
    public DsPredictResponseDTO predict(Object dsRequest) {
        return predictRaw(dsRequest);
    }

    /**
     * Mapea string del DS a enum interno
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
