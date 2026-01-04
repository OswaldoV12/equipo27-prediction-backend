package com.h12_25_l.equipo27.backend.entity;

import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prediccion")
public class Prediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPrevision prevision;

    @Column(nullable = false)
    private Double probabilidad;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor vacío (necesario para JPA)
    public Prediccion() {
    }

    //constructor
    public Prediccion(Vuelo vuelo, TipoPrevision prevision, Double probabilidad) {
        this.vuelo = vuelo;
        this.prevision = prevision;
        this.probabilidad = probabilidad;
        // createdAt se asigna automáticamente por @PrePersist
    }

    // getters / setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public TipoPrevision getPrevision() {
        return prevision;
    }

    public void setPrevision(TipoPrevision prevision) {
        this.prevision = prevision;
    }

    public Double getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(Double probabilidad) {
        this.probabilidad = probabilidad;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}