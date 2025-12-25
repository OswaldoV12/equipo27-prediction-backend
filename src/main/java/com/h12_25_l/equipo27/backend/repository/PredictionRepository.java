package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.model.PredictionRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<PredictionRequest, Long> {
}
