package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import com.h12_25_l.equipo27.backend.repository.AerolineaRepository;
import com.h12_25_l.equipo27.backend.repository.AeropuertoRepository;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import com.h12_25_l.equipo27.backend.repository.VueloRepository;
import com.h12_25_l.equipo27.backend.service.DsApiClient;
import com.h12_25_l.equipo27.backend.service.PredictionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PredictionServiceTest {

    @InjectMocks
    private PredictionService predictionService;
    @Mock
    private DsApiClient dsApiClient;
    @Mock
    private AerolineaRepository aerolineaRepository;
    @Mock
    private AeropuertoRepository aeropuertoRepository;
    @Mock
    private PrediccionRepository prediccionRepository;
    @Mock
    private VueloRepository vueloRepository;

    @Test
    void predictAndSave_shouldThrowException_whenOrigenEqualsDestino(){

        //Arrange
        PredictRequestDTO requestDTO = new PredictRequestDTO(
                "AZ",
                "GIG",
                "GIG",
                LocalDateTime.of(2025, 11, 10, 14,30),
                350
        );

        //Act and Assert
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> predictionService.predictAndSave(requestDTO)
        );

        assertEquals("Origen y destino no pueden ser iguales", exception.getMessage());

        verifyNoInteractions(dsApiClient);
    }

    @Test
    void shouldThrowExceptionWhenDsResponseIsNull(){

        //Arrange
        PredictRequestDTO requestDTO = validRequest();

        when(dsApiClient.predict(requestDTO)).thenReturn(null);

        ExternalServiceException exception = assertThrows(
                ExternalServiceException.class,
                () -> predictionService.predictAndSave(requestDTO)
        );

        assertEquals("Respuesta inválida del modelo DS",exception.getMessage());
    }

    private PredictRequestDTO validRequest() {
        return new PredictRequestDTO(
                "AZ", "GIG", "GRU",
                LocalDateTime.now(), 350
        );
    }

}
