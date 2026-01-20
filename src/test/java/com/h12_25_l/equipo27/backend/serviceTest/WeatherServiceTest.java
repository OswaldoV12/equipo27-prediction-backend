package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.externalapi.WeatherDataDTO;
import com.h12_25_l.equipo27.backend.service.externalapi.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WeatherServiceTest {

    private RestTemplate restTemplate;
    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        // Mock de RestTemplate para no llamar a la API real
        restTemplate = mock(RestTemplate.class);
        weatherService = new WeatherService(restTemplate);
    }

    @Test
    void obtenerClima_shouldReturnData_whenApiResponds() {
        LocalDateTime fecha = LocalDateTime.of(2026, 1, 14, 10, 15);

        // Mock de respuesta de la API
        Map<String, Object> mockResponse = Map.of(
                "hourly", Map.of(
                        "time", List.of("2026-01-14T10:00:00", "2026-01-14T11:00:00"),
                        "temperature_2m", List.of(25.0, 26.0),
                        "wind_speed_10m", List.of(5.0, 6.0),
                        "visibility", List.of(10.0, 12.0)
                )
        );

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        WeatherDataDTO result = weatherService.obtenerClima(10.0, -84.0, fecha);

        assertNotNull(result);
        assertEquals(25.0, result.temperature2m());
        assertEquals(5.0, result.windSpeed10m());
        assertEquals(10.0, result.visibility());
    }

    @Test
    void obtenerClima_shouldReturnDefaults_whenApiFails() {
        LocalDateTime fecha = LocalDateTime.of(2026, 1, 14, 10, 15);

        // Forzar error en la API
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenThrow(new RuntimeException("API caída"));

        WeatherDataDTO result = weatherService.obtenerClima(10.0, -84.0, fecha);

        assertNotNull(result);
        assertEquals(20.0, result.temperature2m());
        assertEquals(5.0, result.windSpeed10m());
        assertEquals(10.0, result.visibility());
    }
}

