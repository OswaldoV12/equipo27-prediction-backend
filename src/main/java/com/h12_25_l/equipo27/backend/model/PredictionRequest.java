package com.h12_25_l.equipo27.backend.model;

import com.h12_25_l.equipo27.backend.dto.PredictRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "requests")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PredictionRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String aerolinea;
    private String origen;
    private String destino;
    private LocalDateTime fechaPartida;
    private Integer distanciaKm;


    public PredictionRequest(PredictRequestDTO datos){
        this.id = null;
        this.aerolinea = datos.aerolinea();
        this.origen = datos.origen();
        this.destino = datos.destino();
        this.fechaPartida = datos.fechaPartida();
        this.distanciaKm = datos.distanciaKm();
    }
}
