package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.core.AerolineaDTO;
import com.h12_25_l.equipo27.backend.service.core.AerolineaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aerolineas")
public class AerolineaController {

    private final AerolineaService aerolineaService;

    public AerolineaController(AerolineaService aerolineaService) {
        this.aerolineaService = aerolineaService;
    }

    @GetMapping
    public ResponseEntity<List<AerolineaDTO>> listarAerolineas() {
        List<AerolineaDTO> aerolineas = aerolineaService.listarTodas();
        return ResponseEntity.ok(aerolineas);
    }
}
