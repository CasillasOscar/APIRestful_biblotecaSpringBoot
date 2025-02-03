package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.Usuario;
import com.proyecto.bibliotecaspring.modelos.UsuarioLogin;
import com.proyecto.bibliotecaspring.modelos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ServiceUsuario implements ServiceUsuarioInterface{

    private UsuarioRepository repoUsuario;

    @Autowired
    public ServiceUsuario(UsuarioRepository repoUsuario){
        this.repoUsuario = repoUsuario;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return repoUsuario.findAll();
    }

    @Override
    public Optional<Usuario> getUsuarioByDNI(String dni) {
        try{

            Optional<Usuario> usuarioExist = repoUsuario.getUsuarioByDni(dni);

            if(usuarioExist.isPresent()){

                return usuarioExist;

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }
    }

    @Override
    public Optional<Usuario> createUsuario(Usuario usuario) {

        if(dniValido(usuario.getDni()) != usuario.getDni().charAt(8)){
            return Optional.empty();
        }

        try{

            Optional<Usuario> usuarioExist = repoUsuario.getUsuarioByDni(usuario.getDni());
            Optional<Usuario> usuarioExist2 = repoUsuario.getUsuarioByEmail(usuario.getEmail());

            if(usuarioExist.isEmpty() && usuarioExist2.isEmpty()){

                repoUsuario.save(usuario);
                return Optional.of(usuario);

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }

    }

    @Override
    public Optional<Usuario> loginUsuario(UsuarioLogin usuarioLogin) {
        
        if(dniValido(usuarioLogin.getDni()) != usuarioLogin.getDni().charAt(8)){
            return Optional.empty();
        }

        try{

            Optional<Usuario> usuarioExist = repoUsuario.getUsuarioByDniAndPassword(usuarioLogin.getDni(), usuarioLogin.getPassword());

            if(usuarioExist.isPresent() &&
                usuarioExist.get().getPassword().compareTo(usuarioLogin.getPassword()) == 0) {

                return usuarioExist;

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }
    }

    @Override
    public Optional<Usuario> updateUsuario(Usuario usuario) {

        try{

            Optional<Usuario> usuarioExist = repoUsuario.getUsuarioByDni(usuario.getDni());

            if(usuarioExist.isPresent()){
                usuarioExist.get().setEmail(usuario.getEmail());
                usuarioExist.get().setNombre(usuario.getNombre());
                usuarioExist.get().setPassword(usuario.getPassword());

                repoUsuario.save(usuarioExist.get());
                return usuarioExist;

            } else {

                return Optional.empty();

            }

        } catch (NoSuchElementException e) {

            return Optional.empty();

        }

    }

    @Override
    public Boolean deleteUsuarioByDNI(String dni) {

        try{

            Optional<Usuario> usuarioExist = repoUsuario.getUsuarioByDni(dni);

            if(usuarioExist.isPresent()){

                repoUsuario.deleteUsuarioByDni(dni);
                return true;

            } else {

                return false;

            }

        } catch (NoSuchElementException e) {

            return false;

        }
    }

    public char dniValido(String dni){
        char[] LETRAS_DNI = {
                'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        Integer numeroDni = Integer.parseInt(dni.substring(0,8));

        int resto = numeroDni % 23;

        return LETRAS_DNI[resto];
    }
}
