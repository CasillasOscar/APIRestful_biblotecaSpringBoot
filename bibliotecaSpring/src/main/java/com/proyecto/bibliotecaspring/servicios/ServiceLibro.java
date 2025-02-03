package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.Libro;
import com.proyecto.bibliotecaspring.modelos.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceLibro implements ServiceLibroInterface{

    public LibroRepository repoLibro;

    @Autowired
    public ServiceLibro(LibroRepository repoLibro){
        this.repoLibro = repoLibro;
    }


    @Override
    public List<Libro> getAllLibros() {
        return repoLibro.findAll();
    }

    @Override
    public Optional<Libro> getLibroByIsbn(String isbn) {
        try{

            Optional<Libro> libro = repoLibro.getLibroByIsbn(isbn);
            return libro;

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }

    }

    @Override
    public Optional<Libro> createLibro(Libro libro) {

        try {

            Optional<Libro> libroExiste = repoLibro.getLibroByIsbn(libro.getIsbn());

            if(libroExiste.isEmpty()){

                repoLibro.save(libro);
                return Optional.of(libro);

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();
        }
    }

    @Override
    public Optional<Libro> updateLibro(Libro libro) {
        try {

            Optional<Libro> libroExiste = repoLibro.getLibroByIsbn(libro.getIsbn());

            if(libroExiste.isPresent()){

                repoLibro.save(libro);
                return Optional.of(libro);

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();
        }
    }

    @Override
    public boolean deleteLibro(String isbn) {
        try {

            Optional<Libro> libroExiste = repoLibro.getLibroByIsbn(isbn);

            if(libroExiste.isPresent()){

                repoLibro.deleteLibroByIsbn(isbn);
                return true;

            } else {

                return false;

            }

        } catch (NoSuchElementException e) {

            return false;
        }
    }
}
