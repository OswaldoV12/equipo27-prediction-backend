package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import com.h12_25_l.equipo27.backend.repository.AerolineaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AerolineaService {

    private final AerolineaRepository aerolineaRepository;

    public AerolineaService(AerolineaRepository aerolineaRepository) {
        this.aerolineaRepository = aerolineaRepository;
    }

    public List<Aerolinea> listarTodas() {
        return aerolineaRepository.findAll();
    }
}
