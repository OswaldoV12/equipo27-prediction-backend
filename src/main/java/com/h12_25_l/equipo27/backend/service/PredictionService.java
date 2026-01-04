package com.h12_25_l.equipo27.backend.service;

import com.h12_25_l.equipo27.backend.client.DsApiClient;
import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import com.h12_25_l.equipo27.backend.dto.PredictResponseDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PredictionService {

    private static final Logger LOG = LoggerFactory.getLogger(PredictionService.class);

    private final DsApiClient dsApiClient;
    private final AerolineaRepository aerolineaRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final VueloRepository vueloRepository;
    private final PrediccionRepository prediccionRepository;

    public PredictionService(DsApiClient dsApiClient,
                             AerolineaRepository aerolineaRepository,
                             AeropuertoRepository aeropuertoRepository,
                             VueloRepository vueloRepository,
                             PrediccionRepository prediccionRepository) {
        this.dsApiClient = dsApiClient;
        this.aerolineaRepository = aerolineaRepository;
        this.aeropuertoRepository = aeropuertoRepository;
        this.vueloRepository = vueloRepository;
        this.prediccionRepository = prediccionRepository;
    }

    @Transactional
    public PredictResponseDTO predictAndSave(PredictRequestDTO request) {

        if (request.origen().equals(request.destino())) {
            LOG.warn("Origen y destino iguales: {}", request.origen());
            throw new ValidationException("Origen y destino no pueden ser iguales");
        }

        // Llamada al DS
        PredictResponseDTO response = dsApiClient.predict(request);
        if (response == null) {
            LOG.warn("Respuesta nula del modelo DS");
            throw new ExternalServiceException("Respuesta inválida del modelo DS");
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

        // Crear y guardar Vuelo
        Vuelo vuelo = new Vuelo(aerolinea, origen, destino, request.fecha_partida(), request.distancia_km());
        vueloRepository.save(vuelo);

        // Crear y guardar Prediccion
        Prediccion prediccion = new Prediccion(vuelo, response.prevision(), response.probabilidad());
        prediccionRepository.save(prediccion);

        return response;
    }
}




