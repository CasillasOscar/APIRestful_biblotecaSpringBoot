package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.Usuario;
import com.proyecto.bibliotecaspring.modelos.UsuarioLogin;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ServiceUsuarioInterface {

    List<Usuario> getAllUsuarios();
    Optional<Usuario> getUsuarioByDNI(String dni);
    Optional<Usuario> createUsuario(Usuario usuario);
    Optional<Usuario> loginUsuario(UsuarioLogin usuarioLogin);
    Optional<Usuario> updateUsuario(Usuario usuario);
    Boolean deleteUsuarioByDNI(String dni);


}
