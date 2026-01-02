package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prediccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Prediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @Column(nullable = false)
    private String prevision;

    @Column(nullable = false)
    private Double probabilidad;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    //constructor
    public Prediccion(Vuelo vuelo, String prevision, Double probabilidad) {
        this.vuelo = vuelo;
        this.prevision = prevision;
        this.probabilidad = probabilidad;
        // createdAt se asigna automáticamente por @PrePersist
    }
}