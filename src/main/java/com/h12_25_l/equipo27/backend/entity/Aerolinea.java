package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aerolinea")
public class Aerolinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 3, nullable = false, unique = true)
    private String iata; // código de 2-3 letras

    // Constructor vacío (necesario para JPA)
    public Aerolinea() {}

    // Constructor con nombre e IATA
    public Aerolinea(String nombre, String iata) {
        this.nombre = nombre;
        this.iata = iata;
    }

    // getters / setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }
}

