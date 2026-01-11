package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.user.UserDTO;
import com.h12_25_l.equipo27.backend.entity.User;
import com.h12_25_l.equipo27.backend.security.TokenJWT;
import com.h12_25_l.equipo27.backend.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/login")
    public ResponseEntity iniciarSesion(@RequestBody @Valid UserDTO user){
        var authenticationToken = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.generarToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWT(tokenJWT));
    }
}
