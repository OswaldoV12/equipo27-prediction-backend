package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.batch.BatchPredictResponseDTO;
import com.h12_25_l.equipo27.backend.service.batch.BatchPredictionService;
import com.h12_25_l.equipo27.backend.service.batch.CsvParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class BatchPredictionController {

    private final CsvParserService csvParserService;
    private final BatchPredictionService batchPredictionService;

    public BatchPredictionController(
            CsvParserService csvParserService,
            BatchPredictionService batchPredictionService) {
        this.csvParserService = csvParserService;
        this.batchPredictionService = batchPredictionService;
    }

    @PostMapping("/predict/batch")
    public ResponseEntity<BatchPredictResponseDTO> batchPredict(
            @RequestParam("file") MultipartFile file) {

        var rows = csvParserService.parse(file);
        var response = batchPredictionService.process(rows);
        return ResponseEntity.ok(response);
    }
}
