package com.h12_25_l.equipo27.backend.serviceTest;

import com.h12_25_l.equipo27.backend.dto.batch.CsvPredictRowDTO;
import com.h12_25_l.equipo27.backend.service.batch.CsvParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserServiceTest {

    private CsvParserService csvParserService;

    @BeforeEach
    void setUp() {
        csvParserService = new CsvParserService();
    }

    @Test
    void parse_shouldReturnRowsForValidCsv() {
        String csvContent = """
                aerolinea,origen,destino,fecha_partida,distancia_km
                AZ,GIG,GRU,2025-11-10T14:30:00,350
                AV,SJO,SYD,2025-12-01T10:00:00,15000""";

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                csvContent.getBytes()
        );

        List<CsvPredictRowDTO> rows = csvParserService.parse(file);

        assertEquals(2, rows.size());

        CsvPredictRowDTO first = rows.get(0);
        assertEquals("AZ", first.aerolinea());
        assertEquals("GIG", first.origen());
        assertEquals("GRU", first.destino());
        assertEquals("2025-11-10T14:30:00", first.fecha_partida());
        assertEquals("350", first.distancia_km());

        CsvPredictRowDTO second = rows.get(1);
        assertEquals("AV", second.aerolinea());
        assertEquals("SJO", second.origen());
        assertEquals("SYD", second.destino());
        assertEquals("2025-12-01T10:00:00", second.fecha_partida());
        assertEquals("15000", second.distancia_km());
    }

    @Test
    void parse_shouldThrowExceptionForInvalidCsv() {
        // CSV con header incorrecto
        String csvContent = "wrong,header,columns\n1,2,3";
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "bad.csv",
                "text/csv",
                csvContent.getBytes()
        );

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> csvParserService.parse(file));

        assertTrue(exception.getMessage().contains("Error al leer el archivo CSV"));
    }
}

