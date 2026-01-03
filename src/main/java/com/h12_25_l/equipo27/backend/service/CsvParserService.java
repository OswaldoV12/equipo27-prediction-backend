package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.dto.CsvPredictRowDTO;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvParserService {

    public List<CsvPredictRowDTO> parse(MultipartFile file) {
        try {
            List<CsvPredictRowDTO> rows = new ArrayList<>();

            // Formato moderno usando CSVFormat.Builder
            CSVFormat format = CSVFormat.Builder.create()
                    .setHeader()             // Primera fila es header
                    .setSkipHeaderRecord(true) // No procesar header como dato
                    .setTrim(true)           // Elimina espacios
                    .build();

            try (InputStreamReader reader =
                         new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
                 CSVParser parser = new CSVParser(reader, format)) {

                for (CSVRecord record : parser) {
                    rows.add(new CsvPredictRowDTO(
                            record.get("aerolinea"),
                            record.get("origen"),
                            record.get("destino"),
                            record.get("fecha_partida"),
                            record.get("distancia_km")
                    ));
                }
            }

            return rows;
        } catch (Exception e) {
            throw new RuntimeException("Error al leer el archivo CSV", e);
        }
    }
}
