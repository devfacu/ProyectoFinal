package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.repository.ProjectRepository;
import java.util.Optional;

import org.springframework.stereotype.Service;

import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProjectService {

	private final ProjectRepository projectRepository;

	public ProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	public Project create(@Validated String name, @Validated User user) throws NotFoundException {
		Project project = new Project();
		validar(name);
		project.setName(name);
		project.setUser(user);

		return projectRepository.save(project);
	}

	public void update(@Validated String id, @Validated String nombre) throws NotFoundException {

		validar(nombre);
		Project project = getById(id);
		project.setName(nombre);

		projectRepository.save(project);
	}

	private Project getById(String id) throws NotFoundException {
		return projectRepository.findById(id)
				.orElseThrow(
						() -> new NotFoundException(String.format("Project with id s% not found.", id))
				);
	}

	public void eliminarProyecto(@Validated String id, @Validated String nombre) throws NotFoundException {

		long cantidadProyecto = projectRepository.count();

		if (cantidadProyecto > 1) {
			Optional<Project> respuesta = projectRepository.findById(id);
			if (respuesta.isPresent()) {
				projectRepository.deleteById(id);
			} else {
				throw new NotFoundException("El proyecto no existe");
			}
		} else {
			throw new NotFoundException("No es posible eliminar todos los proyectos. ");
		}
	}

	public void validar(@Validated String nombre) throws NotFoundException {

		if (nombre == null || nombre.isEmpty()) {
			throw new NotFoundException("El nombre del Proyecto no puede ser nulo");
		}
	}
}
