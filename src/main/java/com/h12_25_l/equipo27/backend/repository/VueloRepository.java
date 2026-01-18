package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    List<Vuelo> findByOrigenIdAndDestinoIdAndFechaPartida(Long origenId, Long destinoId, LocalDateTime fechaPartida);

    @Modifying
    @Query("""
        UPDATE Vuelo v
        SET v.activo = false
        WHERE v.activo = true
        AND v.fechaPartida < :now
    """)
    int desactivarVuelosPasados(@Param("now") LocalDateTime now);
}
