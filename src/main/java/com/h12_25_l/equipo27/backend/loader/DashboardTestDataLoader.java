package com.h12_25_l.equipo27.backend.loader;

import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import com.h12_25_l.equipo27.backend.entity.Aeropuerto;
import com.h12_25_l.equipo27.backend.entity.Vuelo;
import com.h12_25_l.equipo27.backend.entity.Prediccion;
import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import com.h12_25_l.equipo27.backend.repository.AerolineaRepository;
import com.h12_25_l.equipo27.backend.repository.AeropuertoRepository;
import com.h12_25_l.equipo27.backend.repository.VueloRepository;
import com.h12_25_l.equipo27.backend.repository.PrediccionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DashboardTestDataLoader implements CommandLineRunner {

    private final AerolineaRepository aerolineaRepository;
    private final AeropuertoRepository aeropuertoRepository;
    private final VueloRepository vueloRepository;
    private final PrediccionRepository prediccionRepository;

    public DashboardTestDataLoader(AerolineaRepository aerolineaRepository,
                                   AeropuertoRepository aeropuertoRepository,
                                   VueloRepository vueloRepository,
                                   PrediccionRepository prediccionRepository) {
        this.aerolineaRepository = aerolineaRepository;
        this.aeropuertoRepository = aeropuertoRepository;
        this.vueloRepository = vueloRepository;
        this.prediccionRepository = prediccionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vueloRepository.count() > 0) return;

        List<Aerolinea> aerolineas = aerolineaRepository.findAll();
        List<Aeropuerto> aeropuertos = aeropuertoRepository.findAll();

        if (aerolineas.isEmpty() || aeropuertos.size() < 2) return;

        Random random = new Random();

        // Creamos 15 vuelos de prueba con distintas fechas y distancias
        for (int i = 0; i < 15; i++) {
            Aerolinea aerolinea = aerolineas.get(i % aerolineas.size());
            Aeropuerto origen = aeropuertos.get(i % aeropuertos.size());
            Aeropuerto destino = aeropuertos.get((i + 1) % aeropuertos.size());

            Vuelo vuelo = new Vuelo(
                    aerolinea,
                    origen,
                    destino,
                    LocalDateTime.now().plusDays(random.nextInt(10)),
                    500 + random.nextInt(2000)
            );

            vueloRepository.save(vuelo);

            // Creamos predicción aleatoria de "puntual" o "retraso"
            TipoPrevision prevision = random.nextBoolean() ? TipoPrevision.No_Retrasado : TipoPrevision.Retrasado;
            Double probabilidad = 0.5 + (0.5 * random.nextDouble()); // entre 0.5 y 1.0

            Prediccion prediccion = new Prediccion(vuelo, prevision, probabilidad);
            prediccionRepository.save(prediccion);
        }
    }
}
