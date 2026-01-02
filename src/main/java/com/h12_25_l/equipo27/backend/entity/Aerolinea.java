package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aerolinea")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aerolinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 3, nullable = false, unique = true)
    private String iata; // código de 2-3 letras

    // Constructor con nombre e IATA
    public Aerolinea(String nombre, String iata) {
        this.nombre = nombre;
        this.iata = iata;
    }
}

