package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aeropuerto")
@Getter
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

    //constructor
    public Aeropuerto(String nombre, String iata) {
        this.nombre = nombre;
        this.iata = iata;
    }
}
