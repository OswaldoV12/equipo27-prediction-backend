package com.h12_25_l.equipo27.backend.service.externalapi;

import com.h12_25_l.equipo27.backend.dto.externalapi.WeatherDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;

    // Inyección de RestTemplate desde constructor
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherDataDTO obtenerClima(double lat, double lon, LocalDateTime fechaPartida) {
        try {
            int forecastDays = (int) (fechaPartida.toLocalDate().toEpochDay() - LocalDate.now().toEpochDay()) + 1;

            String url = UriComponentsBuilder.fromHttpUrl("https://api.open-meteo.com/v1/forecast")
                    .queryParam("latitude", lat)
                    .queryParam("longitude", lon)
                    .queryParam("hourly", "temperature_2m,wind_speed_10m,visibility")
                    .queryParam("forecast_days", forecastDays)
                    .queryParam("timezone", "auto")
                    .toUriString();

            LOG.info("Llamando a Open-Meteo: {}", url);

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("hourly")) {
                throw new RuntimeException("Respuesta vacía de Open-Meteo");
            }

            Map<String, Object> hourly = (Map<String, Object>) response.get("hourly");
            List<String> times = (List<String>) hourly.get("time");
            List<Double> temps = (List<Double>) hourly.get("temperature_2m");
            List<Double> winds = (List<Double>) hourly.get("wind_speed_10m");
            List<Double> visibility = (List<Double>) hourly.get("visibility");

            LocalDateTime roundedDateTime = redondearHora(fechaPartida);
            int index = encontrarHoraCercana(times, roundedDateTime);

            LOG.info("Hora seleccionada: {}, Temp={}°C, Viento={} km/h, Visibilidad={} km",
                    times.get(index), temps.get(index), winds.get(index), visibility.get(index));

            return new WeatherDataDTO(
                    temps.get(index),
                    winds.get(index),
                    visibility.get(index)
            );

        } catch (Exception e) {
            LOG.warn("Error obteniendo clima: {}. Se usan valores por defecto.", e.getMessage());
            return new WeatherDataDTO(20.0, 5.0, 10.0);
        }
    }

    // -------------------
    // Métodos auxiliares
    // -------------------

    protected LocalDateTime redondearHora(LocalDateTime fechaPartida) {
        int minute = fechaPartida.getMinute();
        if (minute >= 30) {
            return fechaPartida.plusHours(1).withMinute(0).withSecond(0).withNano(0);
        } else {
            return fechaPartida.withMinute(0).withSecond(0).withNano(0);
        }
    }

    protected int encontrarHoraCercana(List<String> times, LocalDateTime targetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        int closestIndex = 0;
        long minDiff = Long.MAX_VALUE;

        for (int i = 0; i < times.size(); i++) {
            LocalDateTime t;
            try {
                t = LocalDateTime.parse(times.get(i), formatter);
            } catch (Exception ex) {
                continue;
            }
            long diff = Math.abs(java.time.Duration.between(t, targetDateTime).toMinutes());
            if (diff < minDiff) {
                minDiff = diff;
                closestIndex = i;
            }
        }

        return closestIndex;
    }
}




