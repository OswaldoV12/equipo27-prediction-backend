package com.h12_25_l.equipo27.backend.service.notificacion;

import com.h12_25_l.equipo27.backend.dto.notificacion.TelegramResponse;
import com.h12_25_l.equipo27.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramService {
    private final UsuarioRepository userRepository;

    public TelegramResponse start(Long chatId) {

        return userRepository.findByChatId(chatId)
                .map(user -> new TelegramResponse(
                        "OK",
                        "Tu cuenta ya está vinculada ✅"
                ))
                .orElse(new TelegramResponse(
                        "EMAIL_REQUIRED",
                        "Por favor envía tu correo para vincular tu cuenta"
                ));
    }

    public TelegramResponse verify(Long chatId, String email, String codigo) {

        var userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return new TelegramResponse(
                    "NOT_FOUND",
                    "No existe un usuario con ese correo. Regístrate en la página."
            );
        }

        // Código simple (luego puedes mejorarlo)
        if (!"1234".equals(codigo)) {
            return new TelegramResponse(
                    "INVALID_CODE",
                    "Código incorrecto"
            );
        }

        var user = userOpt.get();
        user.setChatId(chatId);
        userRepository.save(user);

        return new TelegramResponse(
                "OK",
                "Cuenta vinculada correctamente. Te notificaremos sobre tu vuelo ✈️"
        );
    }
}
