package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.DsMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsMetricsRepository extends JpaRepository<DsMetrics, Long> {
    // No hace falta nada extra, usamos findById(1L) para el registro global
}
