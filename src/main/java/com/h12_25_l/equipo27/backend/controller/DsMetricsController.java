package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.metrics.MetricsDTO;
import com.h12_25_l.equipo27.backend.entity.DsMetrics;
import com.h12_25_l.equipo27.backend.service.metrics.DsMetricsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class DsMetricsController {

    private final DsMetricsService dsMetricsService;

    public DsMetricsController(DsMetricsService dsMetricsService) {
        this.dsMetricsService = dsMetricsService;
    }

    /**
     * Endpoint 1 → Métricas globales de DS
     * Devuelve los totales de requests, exitosas y fallidas.
     */
    @GetMapping("/ds-global")
    public ResponseEntity<DsMetrics> getDsGlobalMetrics() {
        // Se obtiene la fila global de métricas (ID 1)
        DsMetrics metrics = dsMetricsService.obtenerDsMetricsGlobal();
        return ResponseEntity.ok(metrics);
    }

    /**
     * Endpoint 2 → Lista completa de predicciones con detalles de vuelo
     * Combina datos de Prediccion + Vuelo + Aerolinea/Aeropuerto
     */
    @GetMapping("/ds")
    public ResponseEntity<List<MetricsDTO>> getPrediccionesCompletas() {
        List<MetricsDTO> lista = dsMetricsService.listarPrediccionesCompletas();
        return ResponseEntity.ok(lista);
    }
}












