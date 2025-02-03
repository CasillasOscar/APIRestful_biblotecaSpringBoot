package com.proyecto.bibliotecaspring.modelos;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> getUsuariosByDni(String dni);

    void deleteUsuarioByDni(String dni);

    Optional<Usuario> getUsuarioByDni(String dni);

    Optional<Usuario> getUsuarioByEmail(@Email(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Email no válido") String email);

    Optional<Usuario> getUsuarioByDniAndPassword(String dni, String password);
}
