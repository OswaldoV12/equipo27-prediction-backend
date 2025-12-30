package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    List<Vuelo> findByOrigenIdAndDestinoIdAndFechaPartida(Long origenId, Long destinoId, LocalDateTime fechaPartida);
}
