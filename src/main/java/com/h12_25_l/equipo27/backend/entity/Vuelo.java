package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vuelo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aerolinea_id")
    private Aerolinea aerolinea;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origen_id")
    private Aeropuerto origen;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destino_id")
    private Aeropuerto destino;

    @Column(name = "fecha_partida", nullable = false)
    private LocalDateTime fechaPartida;

    @Column(name = "distancia_km", nullable = false)
    private Integer distanciaKm;

    @Column(nullable = false)
    private Boolean activo = true;

    public Vuelo(
            Usuario usuario,
            Aerolinea aerolinea,
            Aeropuerto origen,
            Aeropuerto destino,
            LocalDateTime fechaPartida,
            Integer distanciaKm
    ) {
        this.usuario = usuario;
        this.aerolinea = aerolinea;
        this.origen = origen;
        this.destino = destino;
        this.fechaPartida = fechaPartida;
        this.distanciaKm = distanciaKm;
    }
}