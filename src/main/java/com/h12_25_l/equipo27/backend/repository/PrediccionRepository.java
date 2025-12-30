package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.Prediccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrediccionRepository extends JpaRepository<Prediccion, Long> {
    List<Prediccion> findByVueloId(Long vueloId);
}
