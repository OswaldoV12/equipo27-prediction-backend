package com.h12_25_l.equipo27.backend.service.core;

import com.h12_25_l.equipo27.backend.dto.core.AerolineaDTO;
import com.h12_25_l.equipo27.backend.repository.AerolineaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AerolineaService {

    private final AerolineaRepository aerolineaRepository;

    public AerolineaService(AerolineaRepository aerolineaRepository) {
        this.aerolineaRepository = aerolineaRepository;
    }

    public List<AerolineaDTO> listarTodas() {
        return aerolineaRepository.findAll()
                .stream()
                .map(a -> new AerolineaDTO(
                        a.getIata(),
                        a.getNombre()
                ))
                .toList();
    }
}
