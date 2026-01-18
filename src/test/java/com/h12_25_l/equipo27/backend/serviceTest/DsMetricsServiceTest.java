package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.metrics.MetricsDTO;
import com.h12_25_l.equipo27.backend.entity.DsMetrics;
import com.h12_25_l.equipo27.backend.entity.Prediccion;
import com.h12_25_l.equipo27.backend.entity.Vuelo;
import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import com.h12_25_l.equipo27.backend.entity.Aeropuerto;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.repository.DsMetricsRepository;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import com.h12_25_l.equipo27.backend.service.metrics.DsMetricsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DsMetricsServiceTest {

    private PrediccionRepository prediccionRepository;
    private DsMetricsRepository dsMetricsRepository;
    private DsMetricsService dsMetricsService;

    @BeforeEach
    void setUp() {
        prediccionRepository = mock(PrediccionRepository.class);
        dsMetricsRepository = mock(DsMetricsRepository.class);
        dsMetricsService = new DsMetricsService(prediccionRepository, dsMetricsRepository);
    }

    @Test
    void listarPrediccionesCompletas_shouldMapCorrectly() {
        // Crear mock de vuelo, aerolinea y aeropuertos
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setIata("AZ");
        aerolinea.setNombre("Avianca");

        Aeropuerto origen = new Aeropuerto();
        origen.setIata("SJO");
        origen.setNombre("Juan Santamaría");

        Aeropuerto destino = new Aeropuerto();
        destino.setIata("SYQ");
        destino.setNombre("Tobías Bolaños");

        Vuelo vuelo = new Vuelo();
        vuelo.setAerolinea(aerolinea);
        vuelo.setOrigen(origen);
        vuelo.setDestino(destino);
        vuelo.setFechaPartida(LocalDateTime.of(2026,1,14,10,0));
        vuelo.setDistanciaKm(500);

        Prediccion prediccion = new Prediccion();
        prediccion.setVuelo(vuelo);
        prediccion.setPrevision(TipoPrevision.Retrasado);
        prediccion.setProbabilidad(0.75);
        prediccion.setLatencia(1.2);
        prediccion.setExplicabilidad("Motivo de prueba");

        when(prediccionRepository.findAll()).thenReturn(List.of(prediccion));

        List<MetricsDTO> result = dsMetricsService.listarPrediccionesCompletas();

        assertEquals(1, result.size());
        assertEquals("AZ", result.get(0).aerolineaIata());
        assertEquals("SJO", result.get(0).origenIata());
        assertEquals("SYQ", result.get(0).destinoIata());
    }

    @Test
    void obtenerDsMetricsGlobal_shouldReturnExistingOrCreateNew() {
        DsMetrics existing = new DsMetrics();
        existing.setTotalRequests(5);

        // Caso 1: existe en repo
        when(dsMetricsRepository.findById(1L)).thenReturn(Optional.of(existing));
        DsMetrics result1 = dsMetricsService.obtenerDsMetricsGlobal();
        assertEquals(5, result1.getTotalRequests());

        // Caso 2: no existe → se crea
        when(dsMetricsRepository.findById(1L)).thenReturn(Optional.empty());
        when(dsMetricsRepository.save(any(DsMetrics.class))).thenAnswer(i -> i.getArgument(0));

        DsMetrics result2 = dsMetricsService.obtenerDsMetricsGlobal();
        assertNotNull(result2);
        assertEquals(0, result2.getTotalRequests()); // inicializado en 0
    }
}
