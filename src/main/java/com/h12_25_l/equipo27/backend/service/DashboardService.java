package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.dto.DashboardSummaryDTO;
import com.h12_25_l.equipo27.backend.dto.PredictionHistoryDTO;
import com.h12_25_l.equipo27.backend.entity.Prediccion;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final PrediccionRepository prediccionRepository;

    public DashboardService(PrediccionRepository prediccionRepository) {
        this.prediccionRepository = prediccionRepository;
    }

    // Método para resumen de predicciones
    public DashboardSummaryDTO getSummary() {
        long total = prediccionRepository.count();
        long retrasos = prediccionRepository.findAll()
                .stream()
                .filter(p -> "Retraso".equalsIgnoreCase(p.getPrevision()))
                .count();
        long puntuales = total - retrasos;

        double porcentajeRetrasos = total > 0 ? ((double) retrasos / total) * 100 : 0;
        double porcentajePuntuales = total > 0 ? ((double) puntuales / total) * 100 : 0;

        return new DashboardSummaryDTO(total, puntuales, retrasos, porcentajePuntuales, porcentajeRetrasos);
    }

    // Método para historial temporal por vuelo

    public List<PredictionHistoryDTO> getHistory(Long vueloId) {
        List<Prediccion> predicciones = prediccionRepository.findByVueloId(vueloId);

        return predicciones.stream()
                .sorted((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt()))
                .map(p -> new PredictionHistoryDTO(p.getId(), p.getCreatedAt(), p.getPrevision(), p.getProbabilidad()))
                .collect(Collectors.toList());
    }

    // Nuevo método: historial global de todos los vuelos
    public List<PredictionHistoryDTO> getGlobalHistory() {
        return prediccionRepository.findAll()
                .stream()
                .map(p -> new PredictionHistoryDTO(
                        p.getVuelo().getId(), // agregamos vueloId al DTO
                        p.getCreatedAt(),
                        p.getPrevision(),
                        p.getProbabilidad()
                ))
                .collect(Collectors.toList());
    }
}

