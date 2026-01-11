package com.h12_25_l.equipo27.backend.security;

import com.h12_25_l.equipo27.backend.entity.User;
import com.h12_25_l.equipo27.backend.enums.Roles;
import com.h12_25_l.equipo27.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final TokenService token;
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String username, String email, String password){

        // Encriptar contrasena
        String encryptedPassword = passwordEncoder.encode(password);

        // Crear Usuario
        User user = new User(
                null,
                username,
                email,
                encryptedPassword,
                Roles.USER
        );

        return repository.save(user);
    }

    public User getEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }

    public String login(String email, String password) {
        Optional<User> userOptional = repository.findByEmail(email);
        User user = userOptional.get();

        if (userOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generar token con Auth0
        return token.generateToken(user.getEmail());
    }
}
