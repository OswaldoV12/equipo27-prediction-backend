package com.h12_25_l.equipo27.backend.seguridad;

import com.h12_25_l.equipo27.backend.entity.Usuario;
import com.h12_25_l.equipo27.backend.repository.UsuarioRepository;
import com.h12_25_l.equipo27.backend.service.seguridad.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();

        //  IGNORAR rutas públicas
        if ( path.startsWith("/auth/login") ||
                path.startsWith("/auth/register") ||
                path.startsWith("/api/aerolineas") ||
                path.startsWith("/api/aeropuertos") ||
                path.startsWith("/api/distancia") ||
                path.startsWith("/api/predict") ||
                path.startsWith("/api/metrics")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtenemos del Header
        String authHeader = request.getHeader("Authorization");
        System.out.println("JWT: "+ authHeader);
        if ( authHeader == null ||
                !authHeader.startsWith("Bearer ") ||
                authHeader.equals("Bearer null") ||
                authHeader.equals("Bearer undefined")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7); //  AQUÍ SÍ se limpia
        System.out.println("JWT limpio: " + jwt);
        // Obtenemos el email
        String email = jwtService.getUsernameFromToken(jwt);

        // Si existe email y no hat sesión activa entra
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Usuario user = usuarioRepository.findByEmail(email).orElse(null);
            if (user != null && jwtService.isTokenValid(jwt, email)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_"+user.getRol()))
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}