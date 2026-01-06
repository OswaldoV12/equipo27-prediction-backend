package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aeropuerto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aeropuerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 3, nullable = false, unique = true)
    private String iata;

    @Column(nullable = false)
    private Double latitud;   // nueva columna para latitud

    @Column(nullable = false)
    private Double longitud;  // nueva columna para longitud

    // Constructor antiguo (para compatibilidad)
    public Aeropuerto(String nombre, String iata) {
        this.nombre = nombre;
        this.iata = iata;
    }

    // Nuevo constructor con latitud y longitud
    public Aeropuerto(String nombre, String iata, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.iata = iata;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}

