package com.h12_25_l.equipo27.backend.service.core;

import com.h12_25_l.equipo27.backend.client.DsApiClient;
import com.h12_25_l.equipo27.backend.dto.core.*;
import com.h12_25_l.equipo27.backend.dto.externalapi.WeatherDataDTO;
import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import com.h12_25_l.equipo27.backend.entity.Aeropuerto;
import com.h12_25_l.equipo27.backend.entity.Prediccion;
import com.h12_25_l.equipo27.backend.entity.Vuelo;
import com.h12_25_l.equipo27.backend.exception.ExternalServiceException;
import com.h12_25_l.equipo27.backend.exception.ValidationException;
import com.h12_25_l.equipo27.backend.repository.AerolineaRepository;
import com.h12_25_l.equipo27.backend.repository.AeropuertoRepository;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import com.h12_25_l.equipo27.backend.repository.VueloRepository;
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

        // Validación básica
        if (request.origen().equals(request.destino())) {
            LOG.warn("Origen y destino iguales: {}", request.origen());
            throw new ValidationException("Origen y destino no pueden ser iguales");
        }

        // Buscar entidades maestras en BD
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

        // Obtener datos de clima del aeropuerto de origen con manejo de fallo
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
            clima = new WeatherDataDTO(
                    20.0,  // temperatura por defecto en °C
                    5.0,   // velocidad del viento por defecto en km/h
                    10.0   // visibilidad por defecto en km
            );
            LOG.info("Valores de clima por defecto: Temp={}°C, Viento={} km/h, Visibilidad={} km",
                    clima.temperature2m(), clima.windSpeed10m(), clima.visibility());
        }


        // Crear DTO combinado para enviar a DS
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

        // Llamada al modelo DS
        PredictResponseDTO response = dsApiClient.predict(dsRequest);
        if (response == null) {
            LOG.warn("Respuesta nula del modelo DS");
            throw new ExternalServiceException("Respuesta inválida del modelo DS");
        }

        // Crear y guardar Vuelo
        Vuelo vuelo = new Vuelo(aerolinea, origen, destino, request.fecha_partida(), request.distancia_km());
        vueloRepository.save(vuelo);

        // Crear y guardar Predicción
        Prediccion prediccion = new Prediccion(vuelo, response.prevision(), response.probabilidad());
        prediccionRepository.save(prediccion);

        LOG.info("Predicción realizada y guardada correctamente para vuelo {}-{}",
                request.origen(), request.destino());

        return response;
    }
}





