package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.Prestamo;

import java.util.List;
import java.util.Optional;

public interface ServicePrestamoInterface {

    List<Prestamo> getAllPrestamos();
    Optional<Prestamo> getPrestamoByID(Integer id);
    Optional<Prestamo> reservarEjemplar(String dni, Integer id_ejemplar);
    Optional<Prestamo> devolverPrestamo(String dni, Integer id_ejemplar);
    Boolean deletePrestamosBefore30dias();
}
