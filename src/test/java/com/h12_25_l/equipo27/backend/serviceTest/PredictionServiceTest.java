package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import com.h12_25_l.equipo27.backend.service.PredictionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PredictionServiceTest {

    @InjectMocks
    private PredictionService predictionService;

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
    }
}
