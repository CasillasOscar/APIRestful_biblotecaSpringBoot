package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.Ejemplar;
import java.util.List;
import java.util.Optional;

public interface ServiceEjemplarInterface {

    List<Ejemplar> getAllEjemplares();
    Optional<Ejemplar> getEjemplarById(Integer id);
    Optional<Ejemplar> createEjemplar(String isbn, String estado);
    Optional<Ejemplar> updateEjemplar(Integer id, String estado);
    boolean deleteEjemplar(Integer id);
}
