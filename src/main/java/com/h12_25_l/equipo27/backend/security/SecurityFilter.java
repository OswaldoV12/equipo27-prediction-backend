package com.h12_25_l.equipo27.backend.security;

import com.h12_25_l.equipo27.backend.entity.User;
import com.h12_25_l.equipo27.backend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{

        // Obtenemos del Header
        String authHeader = request.getHeader("Authorization");
        System.out.println("JWT: "+ authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7); //  AQUÍ SÍ se limpia
        System.out.println("JWT limpio: " + jwt);
        // Obtenemos el email
        String email = tokenService.getUsernameFromToken(jwt);

        // Si existe email y no hat sesión activa entra
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Se busca usuario en la base de datos
            User user = repository.findByEmail(email).orElse(null);

            // Si el usuario existe entra y revisa que sea valido
            if (user != null && tokenService.isTokenValid(jwt, email)) {

                // Crear objeto de autenticación
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_"+user.getRol()))
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Para esta requeste el usuario esta autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
