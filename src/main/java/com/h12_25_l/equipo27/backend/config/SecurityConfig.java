package com.h12_25_l.equipo27.backend.config;

import com.h12_25_l.equipo27.backend.seguridad.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())   // Desactivar CSRF para permitir POST desde Postman
                .cors(Customizer.withDefaults())  // Habilitar CORS (aunque desde Postman no es necesario)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/api/aerolineas",
                                "/api/aeropuertos",
                                "/api/distancia",
                                "/api/predict",
                                "/api/predict/batch",
                                "/api/metrics"
                                ).permitAll()  // Rutas públicas
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()             // Todo lo demás protegido
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
