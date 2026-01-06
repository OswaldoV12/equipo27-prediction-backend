package com.h12_25_l.equipo27.backend.service.core;

import com.h12_25_l.equipo27.backend.dto.core.AeropuertoDTO;
import com.h12_25_l.equipo27.backend.repository.AeropuertoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AeropuertoService {

    private final AeropuertoRepository aeropuertoRepository;

    public AeropuertoService(AeropuertoRepository aeropuertoRepository) {
        this.aeropuertoRepository = aeropuertoRepository;
    }

    public List<AeropuertoDTO> listarTodos() {
        return aeropuertoRepository.findAll()
                .stream()
                .map(a -> new AeropuertoDTO(
                        a.getIata(),
                        a.getNombre(),
                        a.getLatitud(),
                        a.getLongitud()
                ))
                .toList();
    }

}
