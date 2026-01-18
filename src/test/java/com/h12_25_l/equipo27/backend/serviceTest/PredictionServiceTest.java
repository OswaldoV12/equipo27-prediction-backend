package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.core.DsPredictResponseDTO;
import com.h12_25_l.equipo27.backend.dto.core.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.core.PredictResponseDTO;
import com.h12_25_l.equipo27.backend.dto.externalapi.WeatherDataDTO;
import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import com.h12_25_l.equipo27.backend.entity.Aeropuerto;
import com.h12_25_l.equipo27.backend.entity.Prediccion;
import com.h12_25_l.equipo27.backend.entity.Vuelo;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import com.h12_25_l.equipo27.backend.repository.AerolineaRepository;
import com.h12_25_l.equipo27.backend.repository.AeropuertoRepository;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import com.h12_25_l.equipo27.backend.repository.VueloRepository;
import com.h12_25_l.equipo27.backend.client.DsApiClient;
import com.h12_25_l.equipo27.backend.service.core.PredictionService;
import com.h12_25_l.equipo27.backend.service.externalapi.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PredictionServiceTest {

    @InjectMocks
    private PredictionService predictionService;

    @Mock
    private DsApiClient dsApiClient;

    @Mock
    private WeatherService weatherService;

    @Mock
    private AerolineaRepository aerolineaRepository;

    @Mock
    private AeropuertoRepository aeropuertoRepository;

    @Mock
    private VueloRepository vueloRepository;

    @Mock
    private PrediccionRepository prediccionRepository;

    // -------------------------
    // VALIDACIONES
    // -------------------------

    @Test
    void shouldThrowException_whenOrigenEqualsDestino() {

        PredictRequestDTO request = new PredictRequestDTO(
                "AZ",
                "GIG",
                "GIG",
                LocalDateTime.of(2025, 11, 10, 14, 30),
                350
        );

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> predictionService.predictAndSave(request)
        );

        assertEquals("Origen y destino no pueden ser iguales", ex.getMessage());

        verifyNoInteractions(dsApiClient, weatherService);
    }

    @Test
    void shouldThrowException_whenAerolineaNotFound() {

        PredictRequestDTO request = validRequest();

        when(aerolineaRepository.findAll()).thenReturn(List.of());

        ValidationException ex = assertThrows(
                ValidationException.class,
                () -> predictionService.predictAndSave(request)
        );

        assertTrue(ex.getMessage().contains("Aerolinea no encontrada"));
    }

    // -------------------------
    // DS + CLIMA
    // -------------------------

    @Test
    void shouldThrowException_whenDsResponseIsNull() {

        PredictRequestDTO request = validRequest();

        when(aerolineaRepository.findAll()).thenReturn(List.of(mockAerolinea()));
        when(aeropuertoRepository.findAll()).thenReturn(List.of(mockOrigen(), mockDestino()));
        when(weatherService.obtenerClima(anyDouble(), anyDouble(), any()))
                .thenReturn(mockWeather());

        when(dsApiClient.predictRaw(any()))
                .thenReturn(null);

        ExternalServiceException ex = assertThrows(
                ExternalServiceException.class,
                () -> predictionService.predictAndSave(request)
        );

        assertEquals("Respuesta inválida del modelo DS", ex.getMessage());
    }

    // -------------------------
    // CASO FELIZ
    // -------------------------

    @Test
    void shouldPredictAndSaveSuccessfully() {

        PredictRequestDTO request = validRequest();

        when(aerolineaRepository.findAll()).thenReturn(List.of(mockAerolinea()));
        when(aeropuertoRepository.findAll()).thenReturn(List.of(mockOrigen(), mockDestino()));
        when(weatherService.obtenerClima(anyDouble(), anyDouble(), any()))
                .thenReturn(mockWeather());

        when(dsApiClient.predictRaw(any()))
                .thenReturn(validDsResponse());

        PredictResponseDTO result = predictionService.predictAndSave(request);

        assertNotNull(result);
        assertEquals(TipoPrevision.Retrasado, result.prevision());
        assertEquals(78.0, result.probabilidad());

        verify(vueloRepository).save(any(Vuelo.class));
        verify(prediccionRepository).save(any(Prediccion.class));
    }

    // -------------------------
    // HELPERS
    // -------------------------

    private PredictRequestDTO validRequest() {
        return new PredictRequestDTO(
                "AZ",
                "GIG",
                "GRU",
                LocalDateTime.of(2025, 11, 10, 14, 30),
                350
        );
    }

    private Aerolinea mockAerolinea() {
        Aerolinea a = new Aerolinea();
        a.setIata("AZ");
        return a;
    }

    private Aeropuerto mockOrigen() {
        Aeropuerto a = new Aeropuerto();
        a.setIata("GIG");
        a.setLatitud(10.0);
        a.setLongitud(-84.0);
        return a;
    }

    private Aeropuerto mockDestino() {
        Aeropuerto a = new Aeropuerto();
        a.setIata("GRU");
        return a;
    }

    private WeatherDataDTO mockWeather() {
        return new WeatherDataDTO(25.0, 10.0, 8.0);
    }

    private DsPredictResponseDTO validDsResponse() {
        return new DsPredictResponseDTO(
                "retrasado",
                0.78,
                1.2,
                "Alta congestión"
        );
    }
}

