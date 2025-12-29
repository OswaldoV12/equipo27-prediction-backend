package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AerolineaRepository extends JpaRepository<Aerolinea, Long> {
    boolean existsByNombre(String nombre);
}
