package com.h12_25_l.equipo27.backend.dto.notificacion;

public record TelegramVerifyRequest (
        Long chatId,
        String email,
        String codigo
){
}
