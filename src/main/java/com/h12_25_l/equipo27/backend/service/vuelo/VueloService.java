package com.h12_25_l.equipo27.backend.service.vuelo;

import com.h12_25_l.equipo27.backend.dto.Vuelos.vueloDTO;
import com.h12_25_l.equipo27.backend.entity.Usuario;
import com.h12_25_l.equipo27.backend.repository.VueloRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VueloService {
    private final VueloRepository vueloRepository;

    public List<vueloDTO> obtenerVuelosDelUsuario(Usuario usuario){
        return vueloRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(v -> new vueloDTO(
                        v.getOrigen().getNombre(),
                        v.getDestino().getNombre(),
                        v.getFechaPartida()
                )).toList();
    }
}
