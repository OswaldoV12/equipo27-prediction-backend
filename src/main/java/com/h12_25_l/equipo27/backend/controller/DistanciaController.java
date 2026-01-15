package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.core.DistanciaDTO;
import com.h12_25_l.equipo27.backend.dto.core.DistanciaRequestDTO;
import com.h12_25_l.equipo27.backend.service.core.DistanciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/distancia")
public class DistanciaController {

    private final DistanciaService distanciaService;

    public DistanciaController(DistanciaService distanciaService) {
        this.distanciaService = distanciaService;
    }

    @PostMapping
    public ResponseEntity<DistanciaDTO> calcularDistancia(@RequestBody DistanciaRequestDTO request) {
        DistanciaDTO distancia = distanciaService.calcularDistancia(request.origen(), request.destino());
        return ResponseEntity.ok(distancia);
    }
}
