package com.proyecto.bibliotecaspring.modelos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {

    Optional<Libro> getLibroByIsbn(String isbn);

    void deleteLibroByIsbn(String isbn);
}
