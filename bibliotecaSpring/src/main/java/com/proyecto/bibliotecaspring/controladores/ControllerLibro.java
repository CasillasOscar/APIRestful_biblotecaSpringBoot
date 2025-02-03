package com.proyecto.bibliotecaspring.controladores;

import com.proyecto.bibliotecaspring.modelos.Libro;
import com.proyecto.bibliotecaspring.servicios.ServiceLibro;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
@CacheConfig(cacheNames = {"libros"})
public class ControllerLibro {

    private final ServiceLibro serviceLibro;

    @Autowired
    public ControllerLibro(ServiceLibro serviceLibro){
        this.serviceLibro = serviceLibro;
    }

    @GetMapping
    public ResponseEntity getAllLibros(){
        List<Libro> listaLibros = serviceLibro.getAllLibros();
        return ResponseEntity.ok(listaLibros);
    }

    @GetMapping("/{isbn}")
    @Cacheable
    public ResponseEntity getLibroByIsbn(@PathVariable String isbn){

        Optional<Libro> libro = serviceLibro.getLibroByIsbn(isbn);

        if(libro.isPresent()){

            return ResponseEntity.ok(libro);

        } else {

            return  ResponseEntity.ok("No se ha encontrado el libro por el isbn");
        }
    }

    @PostMapping
    public ResponseEntity createLibro(@Valid @RequestBody Libro libro){

        Optional<Libro> newLibro = serviceLibro.createLibro(libro);

        if(newLibro.isPresent()){

            return ResponseEntity.ok(newLibro);

        } else {

            return ResponseEntity.ok("No se ha podido crear el libro");

        }
    }


    @PutMapping
    public ResponseEntity updateLibro(@RequestBody Libro libro){

        Optional<Libro> updateLibro = serviceLibro.updateLibro(libro);

        if(updateLibro.isPresent()){

            return ResponseEntity.ok(updateLibro);

        } else {

            return ResponseEntity.ok("No se ha podido actualizar el libro");

        }
    }

    @Transactional
    @DeleteMapping("/{isbn}")
    public ResponseEntity deleteLibro(@PathVariable String isbn){

        Boolean delete = serviceLibro.deleteLibro(isbn);

        if(delete.booleanValue()){

            return ResponseEntity.ok("El libro con isbn " + isbn + " ha sido borrado");

        } else {

            return ResponseEntity.ok("No se ha encontrado un libro con el isbn " + isbn);

        }
    }

}
