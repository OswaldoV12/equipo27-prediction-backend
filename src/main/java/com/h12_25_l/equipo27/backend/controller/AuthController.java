package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.usuario.AuthResponseDTO;
import com.h12_25_l.equipo27.backend.dto.usuario.LoginRequestDTO;
import com.h12_25_l.equipo27.backend.dto.usuario.RegisterRequestDTO;
import com.h12_25_l.equipo27.backend.dto.usuario.UserProfileDTO;
import com.h12_25_l.equipo27.backend.entity.Usuario;
import com.h12_25_l.equipo27.backend.seguridad.SecurityUtils;
import com.h12_25_l.equipo27.backend.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado correctamente");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        String token = userService.login(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }

    //USUARIO
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(Authentication authentication) {
        Usuario usuario = SecurityUtils.getUsuarioActual();
        UserProfileDTO profile = userService.getProfile(usuario);

        if (profile == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profile);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> admin(@RequestBody RegisterRequestDTO request) {

        userService.registerAdmin(
                request.username(),
                request.email(),
                request.password()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Admin creado correctamente");
    }

}