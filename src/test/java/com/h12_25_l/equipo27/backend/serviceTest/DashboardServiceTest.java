package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.dashboard.DashboardSummaryDTO;
import com.h12_25_l.equipo27.backend.dto.dashboard.PredictionHistoryDTO;
import com.h12_25_l.equipo27.backend.entity.Prediccion;
import com.h12_25_l.equipo27.backend.entity.Vuelo;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import com.h12_25_l.equipo27.backend.service.dashboard.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DashboardServiceTest {

    @InjectMocks
    private DashboardService dashboardService;

    @Mock
    private PrediccionRepository prediccionRepository;

    @Test
    void shouldReturnZeroSummaryWhenNoPredictions(){

        when(prediccionRepository.count()).thenReturn(0L);
        when(prediccionRepository.findAll()).thenReturn(List.of());

        DashboardSummaryDTO result = dashboardService.getSummary();

        assertEquals(0, result.totalPredicciones());
        assertEquals(0, result.retrasos());
        assertEquals(0,result.puntuales());
        assertEquals(0.0, result.porcentajeRetrasos());
        assertEquals(0.0, result.porcentajePuntuales());
    }

    @Test
    void shouldCalculateSummaryCorrectly(){

        List<Prediccion> predicciones = List.of(
                mockPrediccion(TipoPrevision.Retrasado),
                mockPrediccion(TipoPrevision.No_Retrasado),
                mockPrediccion(TipoPrevision.Retrasado)

        );

        when(prediccionRepository.count()).thenReturn(3L);
        when(prediccionRepository.findAll()).thenReturn(predicciones);

        DashboardSummaryDTO result = dashboardService.getSummary();

        assertEquals(3, result.totalPredicciones());
        assertEquals(2, result.retrasos());
        assertEquals(1, result.puntuales());
        assertEquals(66.66, result.porcentajeRetrasos(), 0.1);
        assertEquals(33.33, result.porcentajePuntuales(), 0.1);
    }

    @Test
    void shouldReturnHistoryOrderedByDate() {

        Long vueloId = 1L;

        Prediccion older = mockPrediccionWithDate(
                LocalDateTime.now().minusDays(2)
        );
        Prediccion newer = mockPrediccionWithDate(
                LocalDateTime.now()
        );

        when(prediccionRepository.findByVueloId(vueloId))
                .thenReturn(List.of(newer, older));

        List<PredictionHistoryDTO> result =
                dashboardService.getHistory(vueloId);

        assertEquals(2, result.size());
        assertTrue(result.get(0).createdAt()
                .isBefore(result.get(1).createdAt()));
    }

    @Test
    void shouldReturnEmptyHistoryWhenNoPredictions() {

        when(prediccionRepository.findByVueloId(1L))
                .thenReturn(List.of());

        List<PredictionHistoryDTO> result =
                dashboardService.getHistory(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnGlobalHistory() {

        Prediccion p1 = mockPrediccionWithVuelo(1L);
        Prediccion p2 = mockPrediccionWithVuelo(2L);

        when(prediccionRepository.findAll())
                .thenReturn(List.of(p1, p2));

        List<PredictionHistoryDTO> result =
                dashboardService.getGlobalHistory();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).vueloId());
        assertEquals(2L, result.get(1).vueloId());
    }

    private Prediccion mockPrediccion(TipoPrevision prevision) {
        Prediccion p = new Prediccion();
        p.setPrevision(prevision);
        p.setCreatedAt(LocalDateTime.now());
        return p;
    }

    private Prediccion mockPrediccionWithDate(LocalDateTime date) {
        Prediccion p = new Prediccion();
        p.setPrevision(TipoPrevision.Retrasado);
        p.setCreatedAt(date);
        return p;
    }

    private Prediccion mockPrediccionWithVuelo(long vueloId) {
        Vuelo vuelo = new Vuelo();
        vuelo.setId(vueloId);

        Prediccion p = new Prediccion();
        p.setVuelo(vuelo);
        p.setCreatedAt(LocalDateTime.now());
        p.setPrevision(TipoPrevision.No_Retrasado);
        p.setProbabilidad(0.2);

        return p;
    }
}
