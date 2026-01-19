package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.notificacion.TelegramResponse;
import com.h12_25_l.equipo27.backend.dto.notificacion.TelegramStartRequest;
import com.h12_25_l.equipo27.backend.dto.notificacion.TelegramVerifyRequest;
import com.h12_25_l.equipo27.backend.repository.UsuarioRepository;
import com.h12_25_l.equipo27.backend.service.notificacion.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramController {
    private final UsuarioRepository usuarioRepository;

    private final TelegramService telegramService;

    @PostMapping("/start")
    public ResponseEntity<TelegramResponse> start(@RequestBody TelegramStartRequest request) {
        return ResponseEntity.ok(telegramService.start(request.chatId()));
    }

    @PostMapping("/verify")
    public ResponseEntity<TelegramResponse> verify(@RequestBody TelegramVerifyRequest request) {
        return ResponseEntity.ok(
                telegramService.verify(
                        request.chatId(),
                        request.email(),
                        request.codigo()
                )
        );
    }
}
