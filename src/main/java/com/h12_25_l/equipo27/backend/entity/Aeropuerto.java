package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aeropuerto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Aeropuerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 3, nullable = false, unique = true)
    private String iata;
}
