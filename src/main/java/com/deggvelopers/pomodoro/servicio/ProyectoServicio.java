package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.repositorio.ProyectoRepositorio;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProyectoServicio {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    public Proyecto crearProyecto(@Validated String nombre, @Validated Usuario usuario) throws Exception {
        Proyecto proyecto = new Proyecto();

        validar(nombre);

        proyecto.setNombre(nombre);
        proyecto.setUsuario(usuario);
        proyectoRepositorio.save(proyecto);

        return proyecto;
    }

    public void modificar(String id, String nombre) throws Exception {

        validar(nombre);

        Optional<Proyecto> respuesta = proyectoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Proyecto proyecto = proyectoRepositorio.findById(id).get();
            proyecto.setNombre(nombre);

            proyectoRepositorio.save(proyecto);
        } else {

        }
    }

    public void eliminarAutor(String id, String nombre) {

        Optional<Proyecto> respuesta = proyectoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Proyecto proyecto = proyectoRepositorio.findById(id).get();
            if (proyecto != null) {
                proyectoRepositorio.deleteById(id);
            } else {
                System.out.println("No es posible eliminar todos los proyectos. ");
            }
            proyectoRepositorio.save(proyecto);
        } else {

        }
//        if (proyecto != null) {
//            proyectoRepositorio.deleteById(id);
//        } else {
//            System.out.println("No es posible eliminar todos los proyectos. ");
//        }

    }

    public void validar(String nombre) throws Exception {

        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre del Proyecto no puede ser nulo");
        }
    }

}
