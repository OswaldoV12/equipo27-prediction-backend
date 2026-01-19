package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.Usuario;
import com.h12_25_l.equipo27.backend.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRol(Roles rol);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByChatId(Long chatId);
}
