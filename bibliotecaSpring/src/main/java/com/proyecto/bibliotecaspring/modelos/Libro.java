package com.proyecto.bibliotecaspring.modelos;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    @Pattern(regexp = "^\\d{1,5}-\\d{1,5}-\\d{1,5}-\\d{1,5}-\\d{1,5}$", message = "El isbn no tiene el formato correcto")
    @NotBlank(message = "No se permite la entrada con espacios en blanco únicamente en el isbn")
    @NotNull (message = "Se debe enviar el isbn válido")
    @NotEmpty(message = "No se puede enviar el isbn vacío")
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Solo se pueden encribir letras o numeros en el titulo")
    @Size(min = 1, message = "Debe tener mínimo un caracter el titulo")
    @Size(max = 200, message = "Debe tener como máximo 200 caracteres el titulo")
    @NotBlank (message = "No se permite la entrada con espacios en blanco únicamente en el titulo")
    @NotNull (message = "Se debe enviar el titulo")
    @NotEmpty(message = "No se puede enviar el titulo vacío")
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Solo se pueden encribir letras o numeros en el autor")
    @Size(min = 1, message = "Debe tener mínimo un caracter el autor")
    @Size(max = 100, message = "Debe tener como máximo 100 caracteres el autor")
    @NotBlank (message = "No se permite la entrada con espacios en blanco únicamente en el autor")
    @NotNull (message = "Se debe enviar el autor")
    @NotEmpty(message = "No se puede enviar el autor vacío")
    private String autor;

    @OneToMany(mappedBy = "isbn")
    @JsonManagedReference("isbn-libro")
    private Set<Ejemplar> ejemplars = new LinkedHashSet<>();

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Set<Ejemplar> getEjemplars() {
        return ejemplars;
    }

    public void setEjemplars(Set<Ejemplar> ejemplars) {
        this.ejemplars = ejemplars;
    }


}