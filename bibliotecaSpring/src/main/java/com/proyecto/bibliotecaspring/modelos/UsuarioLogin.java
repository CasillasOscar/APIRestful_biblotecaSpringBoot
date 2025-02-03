package com.proyecto.bibliotecaspring.modelos;


public class UsuarioLogin {

    private String dni;
    private String password;


    public UsuarioLogin() {
    }


    public UsuarioLogin(String dni, String password) {
        setDni(dni);
        setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {this.password = password;}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {this.dni = dni;}
}
