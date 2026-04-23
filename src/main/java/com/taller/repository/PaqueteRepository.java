package com.taller.repository;

import com.taller.model.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

    Optional<Paquete> findByNombre(String nombre);
}
