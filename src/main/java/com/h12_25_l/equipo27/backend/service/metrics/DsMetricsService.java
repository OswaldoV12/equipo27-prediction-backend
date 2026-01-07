package com.h12_25_l.equipo27.backend.service.metrics;

import com.h12_25_l.equipo27.backend.dto.metrics.MetricsDTO;
import com.h12_25_l.equipo27.backend.entity.DsMetrics;
import com.h12_25_l.equipo27.backend.repository.DsMetricsRepository;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DsMetricsService {

    private final PrediccionRepository prediccionRepository;
    private final DsMetricsRepository dsMetricsRepository;

    public DsMetricsService(PrediccionRepository prediccionRepository,
                            DsMetricsRepository dsMetricsRepository) {
        this.prediccionRepository = prediccionRepository;
        this.dsMetricsRepository = dsMetricsRepository;
    }

    /**
     * Devuelve la lista completa de predicciones con detalles de vuelo
     */
    public List<MetricsDTO> listarPrediccionesCompletas() {
        return prediccionRepository.findAll()
                .stream()
                .map(p -> new MetricsDTO(
                        p.getVuelo().getAerolinea().getIata(),
                        p.getVuelo().getAerolinea().getNombre(),
                        p.getVuelo().getOrigen().getIata(),
                        p.getVuelo().getOrigen().getNombre(),
                        p.getVuelo().getDestino().getIata(),
                        p.getVuelo().getDestino().getNombre(),
                        p.getVuelo().getFechaPartida(),
                        p.getVuelo().getDistanciaKm(),
                        p.getPrevision(),
                        p.getProbabilidad(),
                        p.getLatencia(),
                        p.getExplicabilidad(),
                        p.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve las métricas globales de DS (total requests, exitosas, fallidas)
     */
    public DsMetrics obtenerDsMetricsGlobal() {
        // Siempre trabajamos con la fila de ID 1
        return dsMetricsRepository.findById(1L)
                .orElseGet(() -> {
                    // Si no existe, la creamos y guardamos
                    DsMetrics metrics = new DsMetrics();
                    return dsMetricsRepository.save(metrics);
                });
    }
}
