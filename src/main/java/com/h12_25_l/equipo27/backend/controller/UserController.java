package com.h12_25_l.equipo27.backend.controller;

import com.h12_25_l.equipo27.backend.dto.user.UserDTO;
import com.h12_25_l.equipo27.backend.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable String username, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(
                new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getRol().name()
                )
        );
    }
}
