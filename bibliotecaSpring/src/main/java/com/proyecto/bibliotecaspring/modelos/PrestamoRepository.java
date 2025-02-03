package com.proyecto.bibliotecaspring.modelos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    Optional<Prestamo> getPrestamosById(Integer id);

    Optional<Prestamo> getPrestamoByUsuario_IdAndEjemplar_Id(Integer usuarioId, Integer ejemplarId);

    void deletePrestamosByFechaDevolucionIsBeforeAndEntregadoEquals(LocalDate localDate, String ok);
}
