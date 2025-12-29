package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vuelo")
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    // Constructor vacío (necesario para JPA)
    public Vuelo() {
    }
    //constructor
    public Vuelo(Aerolinea aerolinea, Aeropuerto origen, Aeropuerto destino, LocalDateTime fechaPartida, Integer distanciaKm) {
        this.aerolinea = aerolinea;
        this.origen = origen;
        this.destino = destino;
        this.fechaPartida = fechaPartida;
        this.distanciaKm = distanciaKm;
    }

    // getters / setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public Aeropuerto getOrigen() {
        return origen;
    }

    public void setOrigen(Aeropuerto origen) {
        this.origen = origen;
    }

    public Aeropuerto getDestino() {
        return destino;
    }

    public void setDestino(Aeropuerto destino) {
        this.destino = destino;
    }

    public LocalDateTime getFechaPartida() {
        return fechaPartida;
    }

    public void setFechaPartida(LocalDateTime fechaPartida) {
        this.fechaPartida = fechaPartida;
    }

    public Integer getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(Integer distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
}