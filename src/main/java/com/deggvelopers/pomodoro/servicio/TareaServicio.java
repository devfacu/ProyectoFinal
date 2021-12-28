package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Configuracion;
import com.deggvelopers.pomodoro.entidad.Prioridad;
import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Tarea;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.ConfiguracionRepositorio;
import com.deggvelopers.pomodoro.repositorio.ProyectoRepositorio;
import com.deggvelopers.pomodoro.repositorio.TareaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TareaServicio {

    @Autowired
    private TareaRepositorio tareaRepo;

    @Autowired
    private ProyectoRepositorio proyectoRepo;

    @Autowired
    private ConfiguracionRepositorio configRepo;

    public void crearTarea(String nombre, Date fecha, String id_proyecto, Prioridad prioridad, Integer cantidadPom, String config_id) throws ErrorServicio {

        validar(nombre);

        Optional<Configuracion> ansConfig = configRepo.findById(config_id);
        if (!ansConfig.isPresent()) {
            throw new ErrorServicio("No se encontro la configuracion al crear la tarea");
        }
        Configuracion config = ansConfig.get();

        Optional<Proyecto> ansProyecto = proyectoRepo.findById(id_proyecto);
        if (!ansProyecto.isPresent()) {
            throw new ErrorServicio("No se encontro el proyecto al crear la tarea");
        }
        Proyecto proyecto = ansProyecto.get();

        Tarea tarea = new Tarea();

        tarea.setNombre(nombre);
        tarea.setFecha(fecha);
        tarea.setProyecto(proyecto);
        tarea.setPrioridad(prioridad);
        tarea.setTiempoInvertido(0);
        tarea.setCompletado(Boolean.FALSE);
        tarea.setCantidadPom(cantidadPom);
        tarea.setDuracionPom(config.getDuracionPom());
        tareaRepo.save(tarea);

//		return tarea;
    }

    public void modificarT(@Validated String id, @Validated String nombre, @Validated Date fecha, @Validated String id_proyecto, @Validated Prioridad prioridad, @Validated Integer cantidadPom) throws ErrorServicio {

        validar(nombre);

        Optional<Tarea> respuesta = tareaRepo.findById(id);

        Proyecto proyecto = proyectoRepo.findById(id_proyecto).get();

        if (respuesta.isPresent()) {
            Tarea tarea = tareaRepo.findById(id).get();
            tarea.setNombre(nombre);
            tarea.setFecha(fecha);
            tarea.setProyecto(proyecto);
            tarea.setPrioridad(prioridad);
            tarea.setCantidadPom(cantidadPom);

            tareaRepo.save(tarea);
        } else {
            throw new ErrorServicio("No se encontro la tarea solicitada");
        }
    }

    public void eliminarT(@Validated String id) throws ErrorServicio {
        tareaRepo.deleteById(id);
    }

    public void validar(@Validated String nombre) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del Proyecto no puede ser nulo");
        }
    }

    public List<Tarea> buscarTareasPorProyectos(List<Proyecto> proyectos, Date fecha) {
        List<Tarea> todasLasTareas = new ArrayList<>();
        proyectos.forEach((proyecto) -> {
            List<Tarea> tareas = tareaRepo.buscarPorFecha(proyecto.getId(), fecha);
            todasLasTareas.addAll(tareas);
        });
        return todasLasTareas;
    }

}
