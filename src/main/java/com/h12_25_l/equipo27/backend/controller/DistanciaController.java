package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.core.DistanciaDTO;
import com.h12_25_l.equipo27.backend.dto.core.DistanciaRequestDTO;
import com.h12_25_l.equipo27.backend.service.core.DistanciaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/distancia")
public class DistanciaController {

    private final DistanciaService distanciaService;

    public DistanciaController(DistanciaService distanciaService) {
        this.distanciaService = distanciaService;
    }

    @PostMapping
    public DistanciaDTO calcularDistancia(@Valid @RequestBody DistanciaRequestDTO request) {
        return distanciaService.calcularDistancia(request.origen(), request.destino());
    }
}
