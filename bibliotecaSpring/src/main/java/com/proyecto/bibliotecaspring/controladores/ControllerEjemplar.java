package com.proyecto.bibliotecaspring.controladores;

import com.proyecto.bibliotecaspring.modelos.Ejemplar;
import com.proyecto.bibliotecaspring.servicios.ServiceEjemplar;
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
@RequestMapping("/ejemplares")
@CacheConfig(cacheNames = {"ejemplares"})
public class ControllerEjemplar {

    private final ServiceEjemplar serviceEjemplar;

    @Autowired
    public ControllerEjemplar(ServiceEjemplar serviceEjemplar){
        this.serviceEjemplar = serviceEjemplar;
    }

    @GetMapping
    public ResponseEntity getAllEjemplares(){

        List <Ejemplar> listaEjemplares = serviceEjemplar.getAllEjemplares();
        return ResponseEntity.ok(listaEjemplares);

    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity getEjemplarById(@PathVariable Integer id){

        Optional<Ejemplar> ejemplar = serviceEjemplar.getEjemplarById(id);

        if(ejemplar.isPresent()){

            return ResponseEntity.ok(ejemplar);

        } else {

            return  ResponseEntity.ok("No se ha encontrado el ejemplar con ese id");
        }
    }


    @PostMapping("/{isbn}/{estado}")
    public ResponseEntity createEjemplar(@Valid @PathVariable String isbn, @Valid @PathVariable String estado){

        Optional<Ejemplar> newEjemplar = serviceEjemplar.createEjemplar(isbn, estado);

        if(newEjemplar.isPresent()){

            return ResponseEntity.ok(newEjemplar);

        } else {

            return ResponseEntity.ok("No se ha podido crear el ejemplar");

        }
    }


    @PutMapping("/{id}/{estado}")
    public ResponseEntity updateEjemplar(@PathVariable Integer id, @Valid @PathVariable String estado){

        Optional<Ejemplar> updateEjemplar = serviceEjemplar.updateEjemplar(id, estado);

        if(updateEjemplar.isPresent()){

            return ResponseEntity.ok(updateEjemplar);

        } else {

            return ResponseEntity.ok("No se ha podido actualizar el ejemplar");

        }
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deleteEjemplar(@PathVariable Integer id){

        Boolean delete = serviceEjemplar.deleteEjemplar(id);

        if(delete.booleanValue()){

            return ResponseEntity.ok("El ejemplar con id " + id + " ha sido borrado");

        } else {

            return ResponseEntity.ok("No se ha podido borrar el ejemplar");

        }
    }


}
