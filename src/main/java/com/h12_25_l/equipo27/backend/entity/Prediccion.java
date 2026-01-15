package com.h12_25_l.equipo27.backend.entity;

import com.h12_25_l.equipo27.backend.enums.TipoPrevision;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "prediccion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vuelo_id")
    private Vuelo vuelo;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoPrevision prevision;

    @Column(nullable = false)
    private Double probabilidad;

    @Column
    private Double latencia;  // nuevo

    @Column
    private String explicabilidad; // nuevo

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor para guardar todo desde DS
    public Prediccion(Vuelo vuelo, TipoPrevision prevision, Double probabilidad,
                      Double latencia, String explicabilidad) {
        this.vuelo = vuelo;
        this.prevision = prevision;
        this.probabilidad = probabilidad;
        this.latencia = latencia;
        this.explicabilidad = explicabilidad;
    }

    // Constructor antiguo para compatibilidad
    public Prediccion(Vuelo vuelo, TipoPrevision prevision, Double probabilidad) {
        this(vuelo, prevision, probabilidad, null, null);
    }
}
