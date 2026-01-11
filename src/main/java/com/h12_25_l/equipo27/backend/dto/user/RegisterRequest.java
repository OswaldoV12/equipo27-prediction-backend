package com.h12_25_l.equipo27.backend.dto.user;

public record RegisterRequest(
        String username,
        String email,
        String password
){
}
