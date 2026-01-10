package com.h12_25_l.equipo27.backend.repository;

import com.h12_25_l.equipo27.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
