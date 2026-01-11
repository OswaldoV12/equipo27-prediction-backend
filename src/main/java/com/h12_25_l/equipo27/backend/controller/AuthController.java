package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.user.LoginRequest;
import com.h12_25_l.equipo27.backend.dto.user.RegisterRequest;
import com.h12_25_l.equipo27.backend.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(
                userService.registerUser(
                        request.username(),
                        request.email(),
                        request.password()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        String token = userService.login(request.email(), request.password());
        return ResponseEntity.ok(token);
    }
}
