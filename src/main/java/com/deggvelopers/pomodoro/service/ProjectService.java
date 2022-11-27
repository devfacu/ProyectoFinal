package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repository.ProjectRepository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project crearProyecto(@Validated String nombre, @Validated User user) throws ErrorServicio {
		Project project = new Project();

		validar(nombre);

		project.setNombre(nombre);
		project.setUsuario(user);
		projectRepository.save(project);

		return project;
	}

	public void modificar(@Validated String id, @Validated String nombre) throws ErrorServicio {

		validar(nombre);

		Optional<Project> respuesta = projectRepository.findById(id);

		if (!respuesta.isPresent()) {
			throw new ErrorServicio("No se encontro el usuario solicitado");
		}
		Project project = projectRepository.findById(id).get();
		project.setNombre(nombre);

		projectRepository.save(project);
	}

	public void eliminarProyecto(@Validated String id, @Validated String nombre) throws ErrorServicio {

		long cantidadProyecto = projectRepository.count();

		if (cantidadProyecto > 1) {
			Optional<Project> respuesta = projectRepository.findById(id);
			if (respuesta.isPresent()) {
				projectRepository.deleteById(id);
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
