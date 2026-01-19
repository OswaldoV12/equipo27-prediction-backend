package com.h12_25_l.equipo27.backend.dto.notificacion;

public record NotificacionPendienteDTO(
        Long id,
        Long chatId,
        String mensaje,
        String tipo
) {
}
