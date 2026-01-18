package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.batch.CsvPredictRowDTO;
import com.h12_25_l.equipo27.backend.dto.core.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.core.PredictResponseDTO;
import com.h12_25_l.equipo27.backend.service.batch.CsvParserService;
import com.h12_25_l.equipo27.backend.service.core.PredictionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PredictionController {

    private final PredictionService predictionService;
    private final CsvParserService csvParserService;

    public PredictionController(PredictionService predictionService,
                                CsvParserService csvParserService) {
        this.predictionService = predictionService;
        this.csvParserService = csvParserService;
    }

    // --- Endpoint original para JSON ---
    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody @Valid List<PredictRequestDTO> requests) {
        List<PredictResponseDTO> responses = predictionService.predictAndSaveMultiple(requests);

        if (responses.size() == 1) {
            return ResponseEntity.ok(responses.get(0));
        } else {
            return ResponseEntity.ok(responses);
        }
    }

    // --- Nuevo endpoint para CSV ---
    @PostMapping("/predict/csv")
    public ResponseEntity<?> predictCsv(@RequestParam("file") MultipartFile file) {
        try {
            List<CsvPredictRowDTO> csvRows = csvParserService.parse(file);

            // Reusar PredictionService para procesar CSV
            Map<String, Object> result = predictionService.predictFromCsv(csvRows);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar CSV: " + e.getMessage());
        }
    }
}


