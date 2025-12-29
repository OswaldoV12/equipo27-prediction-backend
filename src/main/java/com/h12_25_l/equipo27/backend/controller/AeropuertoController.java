package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.entity.Aeropuerto;
import com.h12_25_l.equipo27.backend.service.AeropuertoService;
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
    public List<Aeropuerto> listarAeropuertos() {
        return aeropuertoService.listarTodos();
    }
}
