package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.PredictRequestDetails;
import com.h12_25_l.equipo27.backend.model.PredictionRequest;
import com.h12_25_l.equipo27.backend.repository.PredictionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/predict")
public class PredictionController {

    @Autowired
    private PredictionRepository repository;  //no tiene logica aún

    @Transactional
    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid PredictRequestDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        var request = new PredictionRequest(datos);
        repository.save(request);

        var uri = uriComponentsBuilder.path("/predict/{id}").buildAndExpand(request.getId()).toUri();

        return ResponseEntity.created(uri).body(new PredictRequestDetails(request));
    }
}
