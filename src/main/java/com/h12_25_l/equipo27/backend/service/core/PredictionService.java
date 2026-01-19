package com.h12_25_l.equipo27.backend.service.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.h12_25_l.equipo27.backend.client.DsApiClient;
import com.h12_25_l.equipo27.backend.dto.batch.CsvPredictRowDTO;
import com.h12_25_l.equipo27.backend.dto.core.*;
import com.h12_25_l.equipo27.backend.dto.externalapi.WeatherDataDTO;
import com.h12_25_l.equipo27.backend.entity.*;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import com.h12_25_l.equipo27.backend.repository.*;
import com.h12_25_l.equipo27.backend.service.externalapi.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PredictionService {

    private static final Logger LOG = LoggerFactory.getLogger(PredictionService.class);

    private final DsApiClient dsApiClient;
    private final WeatherService weatherService;
    private final AerolineaRepository aerolineaRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final VueloRepository vueloRepository;
    private final PrediccionRepository prediccionRepository;
    private final ObjectMapper objectMapper;

    public PredictionService(DsApiClient dsApiClient,
                             WeatherService weatherService,
                             AerolineaRepository aerolineaRepository,
                             AeropuertoRepository aeropuertoRepository,
                             VueloRepository vueloRepository,
                             PrediccionRepository prediccionRepository,
                             ObjectMapper objectMapper) {
        this.dsApiClient = dsApiClient;
        this.weatherService = weatherService;
        this.aerolineaRepository = aerolineaRepository;
        this.aeropuertoRepository = aeropuertoRepository;
        this.vueloRepository = vueloRepository;
        this.prediccionRepository = prediccionRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public List<PredictResponseDTO> predictAndSaveMultiple(List<PredictRequestDTO> requests, boolean explain) {
        List<PredictResponseDTO> results = new ArrayList<>();

        for (PredictRequestDTO request : requests) {

            if (request.origen().equals(request.destino()))
                throw new ValidationException("Origen y destino no pueden ser iguales");

            Aerolinea aerolinea = aerolineaRepository.findAll().stream()
                    .filter(a -> a.getIata().equals(request.aerolinea()))
                    .findFirst()
                    .orElseThrow(() -> new ValidationException("Aerolinea no encontrada"));

            Aeropuerto origen = aeropuertoRepository.findAll().stream()
                    .filter(a -> a.getIata().equals(request.origen()))
                    .findFirst()
                    .orElseThrow(() -> new ValidationException("Aeropuerto origen no encontrado"));

            Aeropuerto destino = aeropuertoRepository.findAll().stream()
                    .filter(a -> a.getIata().equals(request.destino()))
                    .findFirst()
                    .orElseThrow(() -> new ValidationException("Aeropuerto destino no encontrado"));

            WeatherDataDTO clima;
            try {
                clima = weatherService.obtenerClima(origen.getLatitud(), origen.getLongitud(), request.fecha_partida());
            } catch (Exception e) {
                clima = new WeatherDataDTO(20.0, 5.0, 10.0);
            }

            DsPredictRequestDTO dsRequest = new DsPredictRequestDTO(
                    request.aerolinea(),
                    request.origen(),
                    request.destino(),
                    request.fecha_partida(),
                    request.distancia_km(),
                    clima.temperature2m(),
                    clima.windSpeed10m(),
                    clima.visibility()
            );

            // --- IMPRIMIR JSON ANTES DE ENVIAR ---
            try {
                String json = objectMapper.writeValueAsString(dsRequest);
                System.out.println("JSON que se enviará a DS: " + json);
            } catch (Exception e) {
                System.out.println("Error serializando request a JSON: " + e.getMessage());
            }


            DsPredictResponseDTO dsResponse = dsApiClient.predictRaw(dsRequest, explain)[0];

            LOG.info("===== DS Response crudo =====\n{}", dsResponse);

            TipoPrevision tipo = DsApiClient.mapPrevisionFromDs(dsResponse.prevision());

            Vuelo vuelo = new Vuelo(aerolinea, origen, destino, request.fecha_partida(), request.distancia_km());
            vueloRepository.save(vuelo);

            // Guardar explicabilidad JSON en BD
            String explicabilidadJson = null;
            String explicabilidadTexto = null;
            if (dsResponse.explicabilidad() != null) {
                try {
                    explicabilidadJson = objectMapper.writeValueAsString(dsResponse.explicabilidad());
                    explicabilidadTexto = dsResponse.explicabilidad().toHumanReadable();
                } catch (Exception e) {
                    LOG.warn("Error serializando explicabilidad: {}", e.getMessage());
                }
            }

            Prediccion prediccion = new Prediccion(vuelo, tipo, dsResponse.probabilidad(),
                    dsResponse.latencia_ms(), explicabilidadJson);
            prediccionRepository.save(prediccion);

            results.add(new PredictResponseDTO(tipo, dsResponse.probabilidad() * 100, explicabilidadTexto));
        }

        return results;
    }

    @Transactional
    public Map<String, Object> predictFromCsv(List<CsvPredictRowDTO> csvRows) {
        List<PredictResponseDTO> responses = new ArrayList<>();
        List<String> errores = new ArrayList<>();

        for (CsvPredictRowDTO row : csvRows) {
            try {
                PredictRequestDTO request = new PredictRequestDTO(
                        row.aerolinea(), row.origen(), row.destino(),
                        LocalDateTime.parse(row.fecha_partida()),
                        Integer.parseInt(row.distancia_km())
                );

                responses.addAll(predictAndSaveMultiple(List.of(request), false)); // batch, explain=false

            } catch (ValidationException ve) {
                errores.add("Fila " + row + ": " + ve.getMessage());
            } catch (Exception e) {
                errores.add("Fila " + row + ": error inesperado");
                LOG.error("Error procesando fila CSV: {}", row, e);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("predicciones", responses);
        result.put("errores", errores);

        return result;
    }
}





