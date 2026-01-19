package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.notificacion.NotificacionPendienteDTO;
import com.h12_25_l.equipo27.backend.enums.EstadoNotificacion;
import com.h12_25_l.equipo27.backend.service.notificacion.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    private final NotificacionService notificacionService;

    @GetMapping("/pendientes")
    public List<NotificacionPendienteDTO> pendientes() {
        return notificacionService.obtenerPendientes();
    }

    @PostMapping("/{id}/ack")
    public ResponseEntity<Void> ack(
            @PathVariable Long id,
            @RequestParam EstadoNotificacion estado
    ) {
        notificacionService.actualizarEstado(id, estado);
        return ResponseEntity.ok().build();
    }
}
