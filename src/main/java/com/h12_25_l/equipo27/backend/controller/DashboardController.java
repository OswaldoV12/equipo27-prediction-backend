package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.dashboard.DashboardSummaryDTO;
import com.h12_25_l.equipo27.backend.dto.dashboard.PredictionHistoryDTO;
import com.h12_25_l.equipo27.backend.service.dashboard.DashboardService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Endpoint para resumen de predicciones
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryDTO> getSummary() {
        DashboardSummaryDTO summary = dashboardService.getSummary();
        return ResponseEntity.ok(summary);
    }

    // Endpoint para historial temporal por vuelo
    @GetMapping("/history")
    public ResponseEntity<List<PredictionHistoryDTO>> getHistory(@RequestParam Long vueloId) {
        List<PredictionHistoryDTO> history = dashboardService.getHistory(vueloId);
        return ResponseEntity.ok(history);
    }

    // Nuevo endpoint: historial global de todos los vuelos
    @GetMapping("/global-history")
    public ResponseEntity<List<PredictionHistoryDTO>> getGlobalHistory() {
        List<PredictionHistoryDTO> predictionHistory = dashboardService.getGlobalHistory();
        return ResponseEntity.ok(predictionHistory);
    }
}

