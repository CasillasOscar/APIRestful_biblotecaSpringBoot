package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.Libro;

import java.util.List;
import java.util.Optional;

public interface ServiceLibroInterface
{
    List<Libro> getAllLibros();
    Optional<Libro> getLibroByIsbn(String isbn);
    Optional<Libro> createLibro(Libro libro);
    Optional<Libro> updateLibro(Libro libro);
    boolean deleteLibro(String isbn);

}
