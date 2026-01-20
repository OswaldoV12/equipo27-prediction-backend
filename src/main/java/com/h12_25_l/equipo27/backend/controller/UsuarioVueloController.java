package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.Vuelos.vueloDTO;
import com.h12_25_l.equipo27.backend.entity.Usuario;
import com.h12_25_l.equipo27.backend.service.vuelo.VueloService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import java.util.List;


@RestController
@RequestMapping("/api/usuario/vuelos")
@PreAuthorize("hasAnyRole('USER','ADMIN')")
@AllArgsConstructor
public class UsuarioVueloController {
    private final VueloService vueloService;

    @GetMapping
    public ResponseEntity<List<vueloDTO>> misVuelos(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        List<vueloDTO> vuelos = vueloService.obtenerVuelosDelUsuario(usuario);
        if (vuelos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(vuelos);
    }
}
