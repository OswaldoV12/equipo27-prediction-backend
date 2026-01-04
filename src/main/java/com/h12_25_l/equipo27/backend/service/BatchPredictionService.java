package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.PredictResponseDTO;
import com.h12_25_l.equipo27.backend.dto.*;
import com.h12_25_l.equipo27.backend.service.PredictionService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BatchPredictionService {

    private final PredictionService predictionService;
    private final Validator validator;

    public BatchPredictionService(PredictionService predictionService, Validator validator) {
        this.predictionService = predictionService;
        this.validator = validator;
    }

    public BatchPredictResponseDTO process(List<CsvPredictRowDTO> rows) {

        List<BatchItemResultDTO> results = new ArrayList<>();
        int ok = 0;
        int error = 0;

        for (CsvPredictRowDTO row : rows) {
            try {
                PredictRequestDTO request = new PredictRequestDTO(
                        row.aerolinea(),
                        row.origen(),
                        row.destino(),
                        LocalDateTime.parse(row.fecha_partida()),
                        Integer.parseInt(row.distancia_km())
                );

                // Validación manual del DTO
                Set<ConstraintViolation<PredictRequestDTO>> violations =
                        validator.validate(request);

                if (!violations.isEmpty()) {
                    String msg = violations.iterator().next().getMessage();
                    results.add(new BatchItemResultDTO(row, null, msg));
                    error++;
                    continue;
                }

                PredictResponseDTO response =
                        predictionService.predictAndSave(request);

                results.add(new BatchItemResultDTO(row, response, null));
                ok++;

            } catch (Exception ex) {
                results.add(new BatchItemResultDTO(
                        row,
                        null,
                        ex.getMessage()
                ));
                error++;
            }
        }

        return new BatchPredictResponseDTO(
                rows.size(),
                ok,
                error,
                results
        );
    }
}

