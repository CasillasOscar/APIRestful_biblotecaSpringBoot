package com.proyecto.bibliotecaspring.servicios;

import com.proyecto.bibliotecaspring.modelos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServicePrestamo implements ServicePrestamoInterface{

    private UsuarioRepository repoUsuario;
    private PrestamoRepository repoPrestamo;
    private EjemplarRepository repoEjemplar;

    @Autowired
    public ServicePrestamo(PrestamoRepository repoPrestamo, UsuarioRepository repoUsuario, EjemplarRepository repoEjemplar) {
        this.repoPrestamo = repoPrestamo;
        this.repoUsuario = repoUsuario;
        this.repoEjemplar = repoEjemplar;
    }

    @Override
    public List<Prestamo> getAllPrestamos() {

        return repoPrestamo.findAll();

    }

    @Override
    public Optional<Prestamo> getPrestamoByID(Integer id) {

        Optional<Prestamo> prestamoExist = repoPrestamo.getPrestamosById(id);

        if(prestamoExist.isPresent()){

            return prestamoExist;

        } else {

            return Optional.empty();

        }
    }

    @Override
    public Optional<Prestamo> reservarEjemplar(String dni, Integer id_ejemplar) {

        Optional<Usuario> usuarioExist = repoUsuario.getUsuarioByDni(dni);

        if(usuarioExist.isPresent()){

            Optional<Ejemplar> ejemplarExist = repoEjemplar.getEjemplarById(id_ejemplar);

            if(ejemplarExist.isPresent()){

                if(ejemplarExist.get().getEstado().equalsIgnoreCase("Disponible")){

                    if(usuarioExist.get().getPenalizacionHasta() == null || usuarioExist.get().getPenalizacionHasta().isBefore(LocalDate.now())){

                        Prestamo prestamo = new Prestamo();
                        prestamo.setUsuario(usuarioExist.get());
                        prestamo.setEjemplar(ejemplarExist.get());
                        prestamo.setFechaInicio(LocalDate.now());
                        prestamo.setFechaDevolucion(LocalDate.now().plusDays(15));
                        prestamo.setEntregado("KO");

                        repoPrestamo.save(prestamo);

                        ejemplarExist.get().setEstado("Prestado");
                        repoEjemplar.save(ejemplarExist.get());

                        return Optional.of(prestamo);

                    } else {

                        return Optional.empty();

                    }

                } else {

                    return Optional.empty();

                }

            } else {

                return Optional.empty();

            }

        } else {

            return Optional.empty();

        }
    }

    @Override
    public Optional<Prestamo> devolverPrestamo(String dni, Integer id_ejemplar) {

        Optional<Usuario> usuarioExist = repoUsuario.getUsuarioByDni(dni);

        if(usuarioExist.isPresent()){

            Optional<Ejemplar> ejemplarExist = repoEjemplar.getEjemplarById(id_ejemplar);

            if(ejemplarExist.isPresent()){

                Optional<Prestamo> prestamoExist = repoPrestamo.getPrestamoByUsuario_IdAndEjemplar_Id(usuarioExist.get().getId(), ejemplarExist.get().getId());

                if(prestamoExist.isPresent()){

                    if(prestamoExist.get().getEntregado().equalsIgnoreCase("KO")){

                        if(prestamoExist.get().getFechaDevolucion().isAfter(LocalDate.now())){

                            ejemplarExist.get().setEstado("Disponible");
                            repoEjemplar.save(ejemplarExist.get());

                            prestamoExist.get().setEntregado("OK");
                            prestamoExist.get().setFechaDevolucion(LocalDate.now());

                            repoPrestamo.save(prestamoExist.get());

                            return prestamoExist;

                        } else {

                            //Penalizamos
                            usuarioExist.get().setPenalizacionHasta(LocalDate.now().plusDays(15));
                            repoUsuario.save(usuarioExist.get());

                            ejemplarExist.get().setEstado("Disponible");
                            repoEjemplar.save(ejemplarExist.get());

                            prestamoExist.get().setEntregado("OK");
                            prestamoExist.get().setFechaDevolucion(LocalDate.now());

                            repoPrestamo.save(prestamoExist.get());

                            return prestamoExist;

                        }

                    } else {

                        return Optional.empty();

                    }

                } else {

                    return Optional.empty();

                }

            } else {

                return Optional.empty();

            }

        } else {

            return Optional.empty();

        }

    }

    @Override
    public Boolean deletePrestamosBefore30dias() {

        repoPrestamo.deletePrestamosByFechaDevolucionIsBeforeAndEntregadoEquals(LocalDate.now().plusDays(-15), "OK");

        return true;
    }
}
