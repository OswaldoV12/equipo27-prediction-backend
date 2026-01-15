package com.h12_25_l.equipo27.backend.service.core;

import com.h12_25_l.equipo27.backend.client.DsApiClient;
import com.h12_25_l.equipo27.backend.dto.core.*;
import com.h12_25_l.equipo27.backend.dto.externalapi.WeatherDataDTO;
import com.h12_25_l.equipo27.backend.entity.*;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import com.h12_25_l.equipo27.backend.repository.*;
import com.h12_25_l.equipo27.backend.service.externalapi.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PredictionService {

    private static final Logger LOG = LoggerFactory.getLogger(PredictionService.class);

    private final DsApiClient dsApiClient;
    private final WeatherService weatherService;
    private final AerolineaRepository aerolineaRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final VueloRepository vueloRepository;
    private final PrediccionRepository prediccionRepository;

    public PredictionService(DsApiClient dsApiClient,
                             WeatherService weatherService,
                             AerolineaRepository aerolineaRepository,
                             AeropuertoRepository aeropuertoRepository,
                             VueloRepository vueloRepository,
                             PrediccionRepository prediccionRepository) {
        this.dsApiClient = dsApiClient;
        this.weatherService = weatherService;
        this.aerolineaRepository = aerolineaRepository;
        this.aeropuertoRepository = aeropuertoRepository;
        this.vueloRepository = vueloRepository;
        this.prediccionRepository = prediccionRepository;
    }

    @Transactional
    public PredictResponseDTO predictAndSave(PredictRequestDTO request) {

        // --- Validaciones básicas ---
        if (request.origen().equals(request.destino())) {
            LOG.warn("Origen y destino iguales: {}", request.origen());
            throw new ValidationException("Origen y destino no pueden ser iguales");
        }

        // --- Buscar entidades maestras en BD ---
        Aerolinea aerolinea = aerolineaRepository.findAll()
                .stream()
                .filter(a -> a.getIata().equals(request.aerolinea()))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Aerolinea no encontrada: " + request.aerolinea()));

        Aeropuerto origen = aeropuertoRepository.findAll()
                .stream()
                .filter(a -> a.getIata().equals(request.origen()))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Aeropuerto origen no encontrado: " + request.origen()));

        Aeropuerto destino = aeropuertoRepository.findAll()
                .stream()
                .filter(a -> a.getIata().equals(request.destino()))
                .findFirst()
                .orElseThrow(() -> new ValidationException("Aeropuerto destino no encontrado: " + request.destino()));

        // --- Obtener clima ---
        WeatherDataDTO clima;
        try {
            clima = weatherService.obtenerClima(
                    origen.getLatitud(),
                    origen.getLongitud(),
                    request.fecha_partida()
            );
            LOG.info("Clima obtenido: Temp={}°C, Viento={} km/h, Visibilidad={} km",
                    clima.temperature2m(), clima.windSpeed10m(), clima.visibility());
        } catch (Exception e) {
            LOG.warn("API de clima caída, usando valores por defecto: {}", e.getMessage());
            clima = new WeatherDataDTO(20.0, 5.0, 10.0);
        }

        // --- Preparar request para DS ---
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

        // --- Llamada al modelo DS ---
        DsPredictResponseDTO dsResponse = dsApiClient.predictRaw(dsRequest);
        if (dsResponse == null) {
            LOG.warn("Respuesta nula del modelo DS");
            throw new ExternalServiceException("Respuesta inválida del modelo DS");
        }

        TipoPrevision tipo = DsApiClient.mapPrevisionFromDs(dsResponse.prevision());

        // --- Guardar Vuelo ---
        Vuelo vuelo = new Vuelo(aerolinea, origen, destino, request.fecha_partida(), request.distancia_km());
        vueloRepository.save(vuelo);

        // --- Guardar Predicción completa ---
        Prediccion prediccion = new Prediccion(
                vuelo,
                tipo,
                dsResponse.probabilidad(),
                dsResponse.latencia(),
                dsResponse.explicabilidad()
        );
        prediccionRepository.save(prediccion);

        LOG.info("Predicción realizada y guardada correctamente para vuelo {}-{}",
                request.origen(), request.destino());

        // --- Retornar solo lo visible al frontend ---
        return new PredictResponseDTO(
                tipo,
                (dsResponse.probabilidad()*100)
        );
    }
}




