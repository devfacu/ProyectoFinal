package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Prioridad;
import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Tarea;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.ProyectoRepositorio;
import com.deggvelopers.pomodoro.repositorio.TareaRepositorio;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TareaServicio {

    @Autowired
    private TareaRepositorio tareaRepositorio;

    @Autowired
    private ProyectoRepositorio proyectoRepo;

    public Tarea crearTarea(@Validated String nombre, @Validated Date fecha, @Validated String id_proyecto, @Validated Prioridad prioridad, @Validated Integer tiempoInvertido, @Validated Boolean Completado, @Validated Integer cantidadPom, @Validated Integer duracionPom) throws ErrorServicio {

        validar(nombre);

        Proyecto proyecto = proyectoRepo.findById(id_proyecto).get();
        Tarea tarea = new Tarea();

        tarea.setNombre(nombre);
        tarea.setFecha(fecha);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad(prioridad);
        tarea.setTiempoInvertido(tiempoInvertido);
        tarea.setCompletado(Completado);
        tarea.setCantidadPom(cantidadPom);
        tarea.setDuracionPom(duracionPom);
        tareaRepositorio.save(tarea);

        return tarea;
    }

    public void modificarT(@Validated String id, @Validated String nombre, @Validated Date fecha, @Validated String id_proyecto, @Validated Prioridad prioridad, @Validated Integer cantidadPom) throws ErrorServicio {

        validar(nombre);

        Optional<Tarea> respuesta = tareaRepositorio.findById(id);

        Proyecto proyecto = proyectoRepo.findById(id_proyecto).get();

        if (respuesta.isPresent()) {
            Tarea tarea = tareaRepositorio.findById(id).get();
            tarea.setNombre(nombre);
            tarea.setFecha(fecha);
            tarea.setProyecto(proyecto);
            tarea.setPrioridad(prioridad);
            tarea.setCantidadPom(cantidadPom);

            tareaRepositorio.save(tarea);
        } else {
            throw new ErrorServicio("No se encontro la tarea solicitada");
        }
    }

    public void eliminarT(@Validated String id) throws ErrorServicio {
        tareaRepositorio.deleteById(id);
    }

    public void validar(@Validated String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del Proyecto no puede ser nulo");
        }
    }
}
