package com.h12_25_l.equipo27.backend.service.scheduler;

import com.h12_25_l.equipo27.backend.repository.VueloRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Setter
public class VueloStatusScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(VueloStatusScheduler.class);
    private final VueloRepository vueloRepository;

    public VueloStatusScheduler(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    @Scheduled(cron = "0 */10 * * * *") // cada 10 minutos
    @Transactional
    public void desactivarVuelosExpirados() {
        int actualizados = vueloRepository.desactivarVuelosPasados(LocalDateTime.now());
        if (actualizados > 0) {
            LOG.info("Vuelos desactivados automáticamente: {}", actualizados);
        }
    }
}
