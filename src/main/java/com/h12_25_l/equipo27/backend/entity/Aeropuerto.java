package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aeropuerto")
public class Aeropuerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 3, nullable = false, unique = true)
    private String iata;

    // Constructor vacío (necesario para JPA)
    public Aeropuerto() {}

    //constructor
    public Aeropuerto(String nombre, String iata) {
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
