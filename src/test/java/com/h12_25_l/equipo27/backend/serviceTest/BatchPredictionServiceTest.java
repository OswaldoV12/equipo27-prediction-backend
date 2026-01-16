package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.batch.*;
import com.h12_25_l.equipo27.backend.dto.core.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.core.PredictResponseDTO;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import com.h12_25_l.equipo27.backend.service.batch.BatchPredictionService;
import com.h12_25_l.equipo27.backend.service.core.PredictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BatchPredictionServiceTest {

    @InjectMocks
    private BatchPredictionService batchPredictionService;

    @Mock
    private PredictionService predictionService;

    @Mock
    private Validator validator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void process_shouldReturnOkForValidRow() throws Exception {
        CsvPredictRowDTO row = new CsvPredictRowDTO(
                "AZ", "GIG", "GRU", "2025-11-10T14:30:00", "350"
        );

        PredictResponseDTO responseDTO = new PredictResponseDTO(TipoPrevision.Retrasado, 78.0);

        when(validator.validate(any(PredictRequestDTO.class)))
                .thenReturn(Collections.emptySet());

        when(predictionService.predictAndSave(any(PredictRequestDTO.class)))
                .thenReturn(responseDTO);

        BatchPredictResponseDTO result = batchPredictionService.process(List.of(row));

        assertEquals(1, result.totalFilas());
        assertEquals(1, result.procesadasOk());
        assertEquals(0, result.conError());
        assertEquals(1, result.resultados().size());
        assertNull(result.resultados().get(0).error());
        assertEquals(TipoPrevision.Retrasado, result.resultados().get(0).output().prevision());

        verify(predictionService).predictAndSave(any(PredictRequestDTO.class));
    }

    @Test
    void process_shouldReturnErrorForValidationFailure() throws Exception {
        CsvPredictRowDTO row = new CsvPredictRowDTO(
                "AZ", "GIG", "GRU", "2025-11-10T14:30:00", "350"
        );

        ConstraintViolation<PredictRequestDTO> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Aerolinea inválida");

        when(validator.validate(any(PredictRequestDTO.class)))
                .thenReturn(Set.of(violation));

        BatchPredictResponseDTO result = batchPredictionService.process(List.of(row));

        assertEquals(1, result.totalFilas());
        assertEquals(0, result.procesadasOk());
        assertEquals(1, result.conError());
        assertEquals("Aerolinea inválida", result.resultados().get(0).error());
        assertNull(result.resultados().get(0).output());

        verify(predictionService, never()).predictAndSave(any());
    }

    @Test
    void process_shouldReturnErrorWhenPredictionServiceThrows() throws Exception {
        CsvPredictRowDTO row = new CsvPredictRowDTO(
                "AZ", "GIG", "GRU", "2025-11-10T14:30:00", "350"
        );

        when(validator.validate(any(PredictRequestDTO.class)))
                .thenReturn(Collections.emptySet());

        when(predictionService.predictAndSave(any(PredictRequestDTO.class)))
                .thenThrow(new ValidationException("Aeropuerto no encontrado"));

        BatchPredictResponseDTO result = batchPredictionService.process(List.of(row));

        assertEquals(1, result.totalFilas());
        assertEquals(0, result.procesadasOk());
        assertEquals(1, result.conError());
        assertEquals("Aeropuerto no encontrado", result.resultados().get(0).error());
        assertNull(result.resultados().get(0).output());
    }
}
