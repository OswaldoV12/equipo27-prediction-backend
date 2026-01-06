package com.h12_25_l.equipo27.backend.service.core;

import com.h12_25_l.equipo27.backend.dto.core.CoordenadasDTO;
import com.h12_25_l.equipo27.backend.dto.core.DistanciaDTO;
import org.springframework.stereotype.Service;

@Service
public class DistanciaService {

    public DistanciaDTO calcularDistancia(CoordenadasDTO origen, CoordenadasDTO destino) {
        double lat1 = Math.toRadians(origen.latitud());
        double lon1 = Math.toRadians(origen.longitud());
        double lat2 = Math.toRadians(destino.latitud());
        double lon2 = Math.toRadians(destino.longitud());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double radioTierraKm = 6371.0;
        double distancia = radioTierraKm * c;

        return new DistanciaDTO(distancia);
    }
}
