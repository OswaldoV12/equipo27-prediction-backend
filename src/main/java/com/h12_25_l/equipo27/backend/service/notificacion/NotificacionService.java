package com.h12_25_l.equipo27.backend.service.notificacion;

import com.h12_25_l.equipo27.backend.dto.notificacion.NotificacionPendienteDTO;
import com.h12_25_l.equipo27.backend.enums.EstadoNotificacion;
import com.h12_25_l.equipo27.backend.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;

    public List<NotificacionPendienteDTO> obtenerPendientes() {

        return notificacionRepository.findPendientes()
                .stream()
                .map(n -> new NotificacionPendienteDTO(
                        n.getId(),
                        n.getUsuario().getChatId(),
                        n.getMensaje(),
                        n.getTipo().name()
                ))
                .toList();
    }

    public void actualizarEstado(Long id, EstadoNotificacion estado) {

        var notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        notificacion.setEstado(estado);
        notificacion.setSentAt(LocalDateTime.now().now());

        notificacionRepository.save(notificacion);
    }
}
