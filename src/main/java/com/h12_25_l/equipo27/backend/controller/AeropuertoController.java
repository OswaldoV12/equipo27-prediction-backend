package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.core.AeropuertoDTO;
import com.h12_25_l.equipo27.backend.service.core.AeropuertoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aeropuertos")
public class AeropuertoController {

    private final AeropuertoService aeropuertoService;

    public AeropuertoController(AeropuertoService aeropuertoService) {
        this.aeropuertoService = aeropuertoService;
    }

    @GetMapping
    public ResponseEntity<List<AeropuertoDTO>> listarAeropuertos() {
        List<AeropuertoDTO> aeropuertos = aeropuertoService.listarTodos();
        return ResponseEntity.ok(aeropuertos);
    }
}
