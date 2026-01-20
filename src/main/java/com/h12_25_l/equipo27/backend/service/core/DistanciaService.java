package com.h12_25_l.equipo27.backend.service.core;

import com.h12_25_l.equipo27.backend.dto.core.CoordenadasDTO;
import com.h12_25_l.equipo27.backend.dto.core.DistanciaDTO;
import org.springframework.stereotype.Service;

@Service
public class DistanciaService {

    private static final double RADIO_TIERRA_KM = 6371.0;

    public DistanciaDTO calcularDistancia(CoordenadasDTO origen, CoordenadasDTO destino) {

        // Regla de negocio: origen y destino no pueden ser iguales
        if (origen.latitud().equals(destino.latitud())
                && origen.longitud().equals(destino.longitud())) {
            throw new IllegalArgumentException("Origen y destino no pueden ser iguales");
        }

        double lat1 = Math.toRadians(origen.latitud());
        double lon1 = Math.toRadians(origen.longitud());
        double lat2 = Math.toRadians(destino.latitud());
        double lon2 = Math.toRadians(destino.longitud());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return new DistanciaDTO(RADIO_TIERRA_KM * c);
    }
}

