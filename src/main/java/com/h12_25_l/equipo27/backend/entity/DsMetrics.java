package com.h12_25_l.equipo27.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ds_metrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DsMetrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int totalRequests = 0;

    @Column(nullable = false)
    private int totalExitosas = 0;

    @Column(nullable = false)
    private int totalFallidas = 0;

    @Column(nullable = false)
    private LocalDateTime lastUpdate = LocalDateTime.now();

    public void incrementSuccess() {
        totalRequests++;
        totalExitosas++;
        lastUpdate = LocalDateTime.now();
    }

    public void incrementFailure() {
        totalRequests++;
        totalFallidas++;
        lastUpdate = LocalDateTime.now();
    }
}


