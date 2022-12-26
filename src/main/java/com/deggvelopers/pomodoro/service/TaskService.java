package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.entity.Configuration;
import com.deggvelopers.pomodoro.entity.Priority;
import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.Task;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.repository.ConfigurationRepository;
import com.deggvelopers.pomodoro.repository.ProjectRepository;
import com.deggvelopers.pomodoro.repository.TaskRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TaskService {

    @Autowired
    private TaskRepository tareaRepo;

    @Autowired
    private ProjectRepository proyectoRepo;

    @Autowired
    private ConfigurationRepository configRepo;

    public void crearTarea(String nombre, Date fecha, String id_proyecto, Priority priority, Integer cantidadPom, String config_id) throws NotFoundException {

        validar(nombre);

        Optional<Configuration> ansConfig = configRepo.findById(config_id);
        if (!ansConfig.isPresent()) {
            throw new NotFoundException("No se encontro la configuracion al crear la tarea");
        }
        Configuration config = ansConfig.get();

        Optional<Project> ansProyecto = proyectoRepo.findById(id_proyecto);
        if (!ansProyecto.isPresent()) {
            throw new NotFoundException("No se encontro el proyecto al crear la tarea");
        }
        Project project = ansProyecto.get();

        Task task = new Task();

        task.setName(nombre);
        task.setDate(fecha);
        task.setProject(project);
        task.setPriority(priority);
        task.setInvestedTime(0);
        task.setDone(Boolean.FALSE);
        task.setPomQuantity(cantidadPom);
	task.setPomFinalized(0);
        task.setPomDuration(config.getDuracionPom());
        tareaRepo.save(task);

//		return tarea;
    }

    public void editarT(@Validated String id, @Validated String nombre, @Validated Date fecha, @Validated String id_proyecto, @Validated Priority priority, @Validated Integer cantidadPom) throws NotFoundException {

        validar(nombre);

        Optional<Task> respuesta = tareaRepo.findById(id);

        Project project = proyectoRepo.findById(id_proyecto).get();

        if (respuesta.isPresent()) {
            Task task = tareaRepo.findById(id).get();
            task.setName(nombre);
            task.setDate(fecha);
            task.setProject(project);
            task.setPriority(priority);
            task.setPomQuantity(cantidadPom);

            tareaRepo.save(task);
        } else {
            throw new NotFoundException("No se encontro la tarea solicitada");
        }
    }

    public void eliminarT(@Validated String id) throws NotFoundException {
        tareaRepo.deleteById(id);
    }

    public void validar(@Validated String nombre) throws NotFoundException {

        if (nombre == null || nombre.isEmpty()) {
            throw new NotFoundException("El nombre del Proyecto no puede ser nulo");
        }
    }

    public List<Task> buscarTareasPorProyectos(List<Project> projects, Date fecha) {
        List<Task> todasLasTasks = new ArrayList<>();
        projects.forEach((proyecto) -> {
            List<Task> tasks = tareaRepo.findByIdAndDate(proyecto.getId(), fecha);
            todasLasTasks.addAll(tasks);
        });
        return todasLasTasks;
    }
	
	public Integer duracionPomTarea(String id){
		Task task = tareaRepo.getById(id);
		Integer minutos = task.getPomDuration();
		return minutos;
	}

	public void switchCompletado(String id) {
		Task task = tareaRepo.getById(id);
		Boolean completado = task.isDone();
		if (completado) {
			task.setDone(false);
		} else {
			task.setDone(true);
		}
		
		tareaRepo.save(task);
	}
	
}
