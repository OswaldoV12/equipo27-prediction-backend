package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.usuario.AuthResponseDTO;
import com.h12_25_l.equipo27.backend.dto.usuario.LoginRequestDTO;
import com.h12_25_l.equipo27.backend.dto.usuario.RegisterRequestDTO;
import com.h12_25_l.equipo27.backend.entity.Usuario;
import com.h12_25_l.equipo27.backend.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        userService.registerUser(
                request.username(),
                request.email(),
                request.password()
        );
        return ResponseEntity.ok("Usuario registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String token = userService.login(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

}
