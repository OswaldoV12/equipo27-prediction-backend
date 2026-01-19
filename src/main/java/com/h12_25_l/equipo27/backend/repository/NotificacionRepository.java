package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Query("""
        SELECT n FROM Notificacion n
        WHERE n.estado = 'PENDING'
    """)
    List<Notificacion> findPendientes();
}
