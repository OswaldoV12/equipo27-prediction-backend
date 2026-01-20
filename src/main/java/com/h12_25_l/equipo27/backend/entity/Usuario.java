package com.h12_25_l.equipo27.backend.entity;

import com.h12_25_l.equipo27.backend.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToMany(mappedBy = "usuario")
    private List<Vuelo> vuelos;

    @Column(unique = true, nullable = true)
    private Long chatId;
}
