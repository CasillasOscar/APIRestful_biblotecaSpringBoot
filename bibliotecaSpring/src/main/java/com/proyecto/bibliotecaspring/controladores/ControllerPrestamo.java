package com.proyecto.bibliotecaspring.controladores;

import com.proyecto.bibliotecaspring.modelos.Prestamo;
import com.proyecto.bibliotecaspring.servicios.ServicePrestamo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prestamos")
@CacheConfig(cacheNames = {"prestamos"})
public class ControllerPrestamo {

    private final ServicePrestamo servicePrestamo;

    @Autowired
    public ControllerPrestamo(ServicePrestamo servicePrestamo) {
        this.servicePrestamo = servicePrestamo;
    }

    @GetMapping
    public ResponseEntity getAllPrestamos(){

        List<Prestamo> listaPrestamos = servicePrestamo.getAllPrestamos();

        return ResponseEntity.ok(listaPrestamos);
    }

    @GetMapping("/{id}")
    @Cacheable
    public ResponseEntity getAllPrestamosById(@PathVariable Integer id){

        Optional<Prestamo> prestamo = servicePrestamo.getPrestamoByID(id);

        if(prestamo.isPresent()){

            return ResponseEntity.ok(prestamo);

        } else {

            return ResponseEntity.ok("No existe un prestamo ese id");

        }
    }


    @PostMapping("{dni}/{id_ejemplar}")
    public ResponseEntity reservarLibro(@PathVariable String dni, @PathVariable Integer id_ejemplar){

        Optional<Prestamo> prestamo = servicePrestamo.reservarEjemplar(dni, id_ejemplar);

        if(prestamo.isPresent()){

            return ResponseEntity.ok(prestamo);

        } else {

            return ResponseEntity.ok("No se ha podido reservar el libro");

        }

    }


    @PutMapping("{dni}/{id_ejemplar}")
    public ResponseEntity devolverLibro(@PathVariable String dni, @PathVariable Integer id_ejemplar){

        Optional<Prestamo> prestamo = servicePrestamo.devolverPrestamo(dni, id_ejemplar);

        if(prestamo.isPresent()){

            return ResponseEntity.ok(prestamo);

        } else {

            return ResponseEntity.ok("No se ha podido devolver el libro");

        }

    }

    @Transactional
    @DeleteMapping
    public ResponseEntity deletePrestamosBefore30dias(){
        Boolean delete = servicePrestamo.deletePrestamosBefore30dias();

        return ResponseEntity.ok("Se han borrado los prestamos de hace 30 días con estado OK");
    }

}
