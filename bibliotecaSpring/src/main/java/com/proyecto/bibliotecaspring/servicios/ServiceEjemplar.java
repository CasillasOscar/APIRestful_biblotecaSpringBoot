package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.Ejemplar;
import com.proyecto.bibliotecaspring.modelos.EjemplarRepository;
import com.proyecto.bibliotecaspring.modelos.Libro;
import com.proyecto.bibliotecaspring.modelos.LibroRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceEjemplar implements ServiceEjemplarInterface{

    public EjemplarRepository repoEjemplar;
    public LibroRepository repoLibro;

    @Autowired
    public ServiceEjemplar(EjemplarRepository repoEjemplar, LibroRepository repoLibro){
        this.repoEjemplar = repoEjemplar;
        this.repoLibro = repoLibro;
    }

    @Override
    public List<Ejemplar> getAllEjemplares() {
        return repoEjemplar.findAll();
    }

    @Override
    public Optional<Ejemplar> getEjemplarById(Integer id) {
        try{

            Optional<Ejemplar> ejemplar = repoEjemplar.getEjemplarById(id);
            System.out.println(ejemplar.get().getIsbn());
            return ejemplar;

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }
    }

    @Override
    public Optional<Ejemplar> createEjemplar(String isbn, String estado) {
        try {

            Optional<Libro> libroExist = repoLibro.getLibroByIsbn(isbn);

            if(libroExist.isPresent()){
                Ejemplar newEjemplar = new Ejemplar();
                newEjemplar.setIsbn(libroExist.get());

            try{
                newEjemplar.setEstado(estado);
                repoEjemplar.save(newEjemplar);
                return Optional.of(newEjemplar);

            } catch (ConstraintViolationException e) {

                return Optional.empty();

            }

            } else {

                return Optional.empty();

            }
        } catch (NoSuchElementException e) {

            return Optional.empty();
        }
    }

    @Override
    public Optional<Ejemplar> updateEjemplar(Integer id, String estado) {
        try {

            Optional<Ejemplar> ejemplarExiste = repoEjemplar.getEjemplarById(id);

            if(ejemplarExiste.isPresent()){

                ejemplarExiste.get().setEstado(estado);
                repoEjemplar.save(ejemplarExiste.get());
                return ejemplarExiste;

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();
        }
    }

    @Override
    public boolean deleteEjemplar(Integer id) {
        try {

            Optional<Ejemplar> ejemplarExiste = repoEjemplar.getEjemplarById(id);

            if(ejemplarExiste.isPresent()){

                repoEjemplar.deleteById(id);
                return true;

            } else {

                return false;

            }

        } catch (NoSuchElementException e) {

            return false;
        }
    }
}
