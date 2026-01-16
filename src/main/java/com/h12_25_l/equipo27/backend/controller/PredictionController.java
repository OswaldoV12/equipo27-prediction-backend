package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.core.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.service.core.PredictionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody @Valid PredictRequestDTO request) {
        var response = predictionService.predictAndSave(request);
        return ResponseEntity.ok(response);
    }
}
