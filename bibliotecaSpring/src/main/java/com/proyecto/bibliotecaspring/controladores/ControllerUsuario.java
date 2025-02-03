package com.proyecto.bibliotecaspring.controladores;


import com.proyecto.bibliotecaspring.modelos.Usuario;
import com.proyecto.bibliotecaspring.modelos.UsuarioLogin;
import com.proyecto.bibliotecaspring.servicios.ServiceUsuario;
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
@RequestMapping("/usuarios")
@CacheConfig(cacheNames = {"usuarios"})
public class ControllerUsuario {

    private final ServiceUsuario serviceUsuario;

    @Autowired
    public ControllerUsuario(ServiceUsuario serviceUsuario) {
        this.serviceUsuario = serviceUsuario;
    }

    @GetMapping
    public ResponseEntity getAllUsuarios(){

        List<Usuario> listaUsuarios = serviceUsuario.getAllUsuarios();
        return ResponseEntity.ok(listaUsuarios);

    }

    @GetMapping("/{dni}")
    @Cacheable
    public ResponseEntity getUsuarioById(@PathVariable String dni){

        Optional<Usuario> usuarioExist = serviceUsuario.getUsuarioByDNI(dni);

        if(usuarioExist.isPresent()){

            return ResponseEntity.ok(usuarioExist);

        } else {

            return ResponseEntity.ok("No se ha encontrado dicho usuario con el dni");

        }
    }

    @PostMapping
    public ResponseEntity createUsuario(@Valid @RequestBody Usuario usuario){

        Optional<Usuario> newUsuario = serviceUsuario.createUsuario(usuario);

        if(newUsuario.isPresent()){

            return ResponseEntity.ok(usuario);

        } else {

            return ResponseEntity.ok("El usuario no ha podido ser creado");

        }
    }


    @PostMapping("/login")
    public ResponseEntity loginUsuario(@RequestBody UsuarioLogin usuarioLogin) {

        if (!usuarioLogin.getPassword().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{4,12}$")) {

            return ResponseEntity.badRequest().body("Tiene que tener entre 4 y 12 caracteres con una letra mayúscula, una minúscula y un número al menos");

        } else {

                if (!usuarioLogin.getDni().matches("^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$")) {

                    return ResponseEntity.badRequest().body("Estructura dni 8 digitos 1 letra");

                } else {

                    Optional<Usuario> usuario = serviceUsuario.loginUsuario(usuarioLogin);

                    if (usuario.isPresent()) {

                        return ResponseEntity.ok(usuario);

                    } else {

                        return ResponseEntity.ok("El usuario no se ha encontrado");

                    }
                }
        }
    }

    @PutMapping
    public ResponseEntity updateUsuario(@RequestBody Usuario usuario){

        Optional<Usuario> newUsuario = serviceUsuario.updateUsuario(usuario);

        if(newUsuario.isPresent()){

            return ResponseEntity.ok(usuario);

        } else {

            return ResponseEntity.ok("El usuario no ha podido ser actualizado");

        }
    }

    @Transactional
    @DeleteMapping("/{dni}")
    public ResponseEntity deleteUsuarioByDni(@PathVariable String dni){

        Boolean delete = serviceUsuario.deleteUsuarioByDNI(dni);

        if(delete.booleanValue()){

            return ResponseEntity.ok("El usuario con dni " + dni + " ha sido eliminado");

        } else {

            return ResponseEntity.ok("El usuario no ha podido ser elminado");

        }
    }

}
