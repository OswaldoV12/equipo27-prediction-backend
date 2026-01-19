package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.usuario.ListaUsuariosDTO;
import com.h12_25_l.equipo27.backend.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserController {
    private final UsuarioService usuarioService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/usuarios")
    public ResponseEntity<List<ListaUsuariosDTO>> getUsuarios(){
        List<ListaUsuariosDTO> usuarios = usuarioService.listaUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }
}
