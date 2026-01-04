package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.AerolineaDTO;
import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import com.h12_25_l.equipo27.backend.service.AerolineaService;
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
    public List<AerolineaDTO> listarAerolineas() {
        return aerolineaService.listarTodas();
    }
}
