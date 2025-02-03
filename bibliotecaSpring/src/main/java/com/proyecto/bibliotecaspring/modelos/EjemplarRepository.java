package com.proyecto.bibliotecaspring.modelos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {
    Optional<Ejemplar> getEjemplarsById(Integer id);

    Optional<Ejemplar> getEjemplarById(Integer id);
}
