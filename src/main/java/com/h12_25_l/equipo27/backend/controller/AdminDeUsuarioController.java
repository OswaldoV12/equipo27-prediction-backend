package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.usuario.ListaUsuariosDTO;
import com.h12_25_l.equipo27.backend.repository.UsuarioRepository;
import com.h12_25_l.equipo27.backend.service.usuario.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/usuarioos")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminDeUsuarioController {
    private final UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<ListaUsuariosDTO>> listarUsuarios(){
        List<ListaUsuariosDTO> usuarios = usuarioService.obteneerUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarios);
    }
}
