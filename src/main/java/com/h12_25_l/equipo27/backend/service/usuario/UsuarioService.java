package com.h12_25_l.equipo27.backend.service.usuario;

import com.h12_25_l.equipo27.backend.entity.Usuario;
import com.h12_25_l.equipo27.backend.repository.UsuarioRepository;
import com.h12_25_l.equipo27.backend.service.seguridad.JwtService;
import com.h12_25_l.equipo27.backend.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final JwtService jwtService;
    private final UsuarioRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario registerUser(String username, String email, String password){

        // Encriptar contrasena
        String encryptedPassword = passwordEncoder.encode(password);

        // Crear Usuario
        Usuario user = new Usuario();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRol(Roles.USER);
        user.setChatId(null);

        return userRepository.save(user);
    }

    public Usuario getEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public String login(String email, String password) {
        Optional<Usuario> userOptional = userRepository.findByEmail(email);
        Usuario user = userOptional.get();

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar token con Auth0
        return jwtService.generateToken(user.getEmail());
    }
}
