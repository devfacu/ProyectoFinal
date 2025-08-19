package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.repository.ProjectRepository;

import java.util.List;
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
		validate(name);
		project.setName(name);
		project.setUser(user);

		return projectRepository.save(project);
	}

	public void update(@Validated String id, @Validated String name) throws NotFoundException {

		validate(name);
		Project project = getById(id);
		project.setName(name);

		projectRepository.save(project);
	}

	private Project getById(String id) throws NotFoundException {
		return projectRepository.findById(id)
				.orElseThrow(
						() -> new NotFoundException(String.format("Project with id s% not found.", id))
				);
	}

	public void eliminateProtect(@Validated String id, @Validated String name) throws NotFoundException {

		long projectQuantity = projectRepository.count();

		if (projectQuantity > 1) {
			Optional<Project> response = projectRepository.findById(id);
			if (response.isPresent()) {
				projectRepository.deleteById(id);
			} else {
				throw new NotFoundException("El proyecto no existe");
			}
		} else {
			throw new NotFoundException("No es posible eliminar todos los proyectos. ");
		}
	}

	public void validate(@Validated String name) throws NotFoundException {

		if (name == null || name.isEmpty()) {
			throw new NotFoundException("El nombre del Proyecto no puede ser nulo");
		}
	}

	public List<Project> findByUserId(String userId) {

        return projectRepository.findByUserId(userId);
	}
}
