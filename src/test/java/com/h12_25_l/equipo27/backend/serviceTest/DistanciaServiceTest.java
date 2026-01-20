package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.core.CoordenadasDTO;
import com.h12_25_l.equipo27.backend.dto.core.DistanciaDTO;
import com.h12_25_l.equipo27.backend.service.core.DistanciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanciaServiceTest {

    private DistanciaService distanciaService;

    @BeforeEach
    void setUp() {
        distanciaService = new DistanciaService();
    }

    @Test
    void calcularDistancia_shouldReturnZero_whenOrigenEqualsDestino() {
        CoordenadasDTO punto = new CoordenadasDTO(10.0, -84.0);

        DistanciaDTO resultado = distanciaService.calcularDistancia(punto, punto);

        assertEquals(0.0, resultado.distanciaKm(), 0.0001);
    }

    @Test
    void calcularDistancia_shouldReturnCorrectDistance_betweenKnownPoints() {
        // Aeropuerto SJO (Costa Rica) → LAX (Los Angeles)
        CoordenadasDTO sjo = new CoordenadasDTO(9.9937, -84.2088);
        CoordenadasDTO lax = new CoordenadasDTO(33.9416, -118.4085);

        DistanciaDTO resultado = distanciaService.calcularDistancia(sjo, lax);

        // Aproximadamente 6100 km
        assertEquals(6100, resultado.distanciaKm(), 50); // delta 50 km
    }

    @Test
    void calcularDistancia_shouldBeSymmetric() {
        CoordenadasDTO a = new CoordenadasDTO(10.0, -84.0);
        CoordenadasDTO b = new CoordenadasDTO(20.0, -80.0);

        double ab = distanciaService.calcularDistancia(a, b).distanciaKm();
        double ba = distanciaService.calcularDistancia(b, a).distanciaKm();

        assertEquals(ab, ba, 0.0001);
    }

    @Test
    void calcularDistancia_shouldHandleClosePoints() {
        CoordenadasDTO a = new CoordenadasDTO(10.0, -84.0);
        CoordenadasDTO b = new CoordenadasDTO(10.001, -84.001);

        DistanciaDTO resultado = distanciaService.calcularDistancia(a, b);

        assertTrue(resultado.distanciaKm() > 0);
        assertTrue(resultado.distanciaKm() < 0.2); // puntos muy cercanos
    }
}
