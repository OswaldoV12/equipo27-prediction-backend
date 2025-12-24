package com.h12_25_l.equipo27.backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PredictionController {

    private final PredictionService predictionService; //aca falta crear el archivo

    @PostMapping("/predict")
    public ResponseEntity<?> predict(@RequestBody @Valid PredictRequestDTO request) {
        var response = predictionService.predict(request);
        return ResponseEntity.ok(response);
    }
}
