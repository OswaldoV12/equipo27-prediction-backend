package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.entity.Aeropuerto;
import com.h12_25_l.equipo27.backend.repository.AeropuertoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AeropuertoService {

    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoService(AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
    }

    public List<Aeropuerto> listarTodos() {
        return aeropuertoRepository.findAll();
    }
}
