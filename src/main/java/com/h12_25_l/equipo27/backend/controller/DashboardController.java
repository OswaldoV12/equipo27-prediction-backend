package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.dashboard.DashboardSummaryDTO;
import com.h12_25_l.equipo27.backend.dto.dashboard.PredictionHistoryDTO;
import com.h12_25_l.equipo27.backend.service.dashboard.DashboardService;
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
    public DashboardSummaryDTO getSummary() {
        return dashboardService.getSummary();
    }

    // Endpoint para historial temporal por vuelo
    @GetMapping("/history")
    public List<PredictionHistoryDTO> getHistory(@RequestParam Long vueloId) {
        return dashboardService.getHistory(vueloId);
    }

    // Nuevo endpoint: historial global de todos los vuelos
    @GetMapping("/global-history")
    public List<PredictionHistoryDTO> getGlobalHistory() {
        return dashboardService.getGlobalHistory();
    }
}

