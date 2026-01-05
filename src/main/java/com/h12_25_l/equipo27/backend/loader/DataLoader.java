/*package com.h12_25_l.equipo27.backend.loader;

import com.h12_25_l.equipo27.backend.entity.Aerolinea;
import com.h12_25_l.equipo27.backend.entity.Aeropuerto;
import com.h12_25_l.equipo27.backend.repository.AerolineaRepository;
import com.h12_25_l.equipo27.backend.repository.AeropuertoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AerolineaRepository aerolineaRepository;
    private final AeropuertoRepository aeropuertoRepository;

    public DataLoader(AerolineaRepository aerolineaRepository,
                      AeropuertoRepository aeropuertoRepository) {
        this.aerolineaRepository = aerolineaRepository;
        this.aeropuertoRepository = aeropuertoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (aerolineaRepository.count() == 0) {
            aerolineaRepository.save(new Aerolinea("Avianca", "AV"));
            aerolineaRepository.save(new Aerolinea("United Airlines", "UA"));
            aerolineaRepository.save(new Aerolinea("American Airlines", "AA"));
            aerolineaRepository.save(new Aerolinea("Delta Air Lines", "DL"));
            aerolineaRepository.save(new Aerolinea("LATAM Airlines", "LA"));
            aerolineaRepository.save(new Aerolinea("Copa Airlines", "CM"));
            aerolineaRepository.save(new Aerolinea("Aeroméxico", "AM"));
            aerolineaRepository.save(new Aerolinea("Air France", "AF"));
            aerolineaRepository.save(new Aerolinea("KLM Royal Dutch Airlines", "KL"));
            aerolineaRepository.save(new Aerolinea("Iberia", "IB"));
            aerolineaRepository.save(new Aerolinea("British Airways", "BA"));
            aerolineaRepository.save(new Aerolinea("Lufthansa", "LH"));
            aerolineaRepository.save(new Aerolinea("Emirates", "EK"));
            aerolineaRepository.save(new Aerolinea("Qatar Airways", "QR"));
            aerolineaRepository.save(new Aerolinea("Turkish Airlines", "TK"));
            aerolineaRepository.save(new Aerolinea("JetBlue", "B6"));
            aerolineaRepository.save(new Aerolinea("Spirit Airlines", "NK"));
            aerolineaRepository.save(new Aerolinea("Alaska Airlines", "AS"));
            aerolineaRepository.save(new Aerolinea("Air Canada", "AC"));
        }

        if (aeropuertoRepository.count() == 0) {
            aeropuertoRepository.save(new Aeropuerto("Juan Santamaría International Airport", "SJO"));
            aeropuertoRepository.save(new Aeropuerto("Tobías Bolaños Airport", "SYQ"));

            aeropuertoRepository.save(new Aeropuerto("Los Angeles International Airport", "LAX"));
            aeropuertoRepository.save(new Aeropuerto("John F. Kennedy International Airport", "JFK"));
            aeropuertoRepository.save(new Aeropuerto("Miami International Airport", "MIA"));
            aeropuertoRepository.save(new Aeropuerto("Chicago O'Hare International Airport", "ORD"));
            aeropuertoRepository.save(new Aeropuerto("Dallas/Fort Worth International Airport", "DFW"));
            aeropuertoRepository.save(new Aeropuerto("San Francisco International Airport", "SFO"));
            aeropuertoRepository.save(new Aeropuerto("Hartsfield–Jackson Atlanta International Airport", "ATL"));

            aeropuertoRepository.save(new Aeropuerto("El Dorado International Airport", "BOG"));
            aeropuertoRepository.save(new Aeropuerto("Jorge Chávez International Airport", "LIM"));
            aeropuertoRepository.save(new Aeropuerto("Arturo Merino Benítez International Airport", "SCL"));
            aeropuertoRepository.save(new Aeropuerto("Ezeiza International Airport", "EZE"));

            aeropuertoRepository.save(new Aeropuerto("Adolfo Suárez Madrid–Barajas Airport", "MAD"));
            aeropuertoRepository.save(new Aeropuerto("Barcelona–El Prat Airport", "BCN"));
            aeropuertoRepository.save(new Aeropuerto("Charles de Gaulle Airport", "CDG"));
            aeropuertoRepository.save(new Aeropuerto("Amsterdam Schiphol Airport", "AMS"));
            aeropuertoRepository.save(new Aeropuerto("Frankfurt Airport", "FRA"));
            aeropuertoRepository.save(new Aeropuerto("London Heathrow Airport", "LHR"));

            aeropuertoRepository.save(new Aeropuerto("Dubai International Airport", "DXB"));
            aeropuertoRepository.save(new Aeropuerto("Doha Hamad International Airport", "DOH"));
            aeropuertoRepository.save(new Aeropuerto("Istanbul Airport", "IST"));

            aeropuertoRepository.save(new Aeropuerto("Tokyo Haneda Airport", "HND"));
            aeropuertoRepository.save(new Aeropuerto("Tokyo Narita Airport", "NRT"));
        }

    }
}
*/