package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
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

    public Proyecto crearProyecto(@Validated String nombre, @Validated Usuario usuario) throws ErrorServicio {
        Proyecto proyecto = new Proyecto();

        validar(nombre);

        proyecto.setNombre(nombre);
        proyecto.setUsuario(usuario);
        proyectoRepositorio.save(proyecto);

        return proyecto;
    }

    public void modificar(@Validated String id, @Validated String nombre) throws ErrorServicio {

        validar(nombre);

        Optional<Proyecto> respuesta = proyectoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Proyecto proyecto = proyectoRepositorio.findById(id).get();
            proyecto.setNombre(nombre);

            proyectoRepositorio.save(proyecto);
        } else {
            throw new ErrorServicio("No se encontro el usuario solicitado");
        }
    }

    public void eliminarProyecto(@Validated String id, @Validated String nombre) throws ErrorServicio {

        long cantidadProyecto = proyectoRepositorio.count();

        if (cantidadProyecto > 1) {
            Optional<Proyecto> respuesta = proyectoRepositorio.findById(id);
            if (respuesta.isPresent()) {
                Proyecto proyecto = respuesta.get();
                proyectoRepositorio.deleteById(id);
            } else {
                throw new ErrorServicio("El proyecto no existe");
            }
        } else {
            throw new ErrorServicio("No es posible eliminar todos los proyectos. ");
        }
    }

    public void validar(@Validated String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del Proyecto no puede ser nulo");
        }
    }
}
