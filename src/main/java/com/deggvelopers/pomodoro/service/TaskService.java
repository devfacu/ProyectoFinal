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
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    public void create(String name, Date date, String project_id, Priority priority, Integer pomQuantity, String config_id) throws NotFoundException {

        validate(name);

        Optional<Configuration> ansConfig = configurationRepository.findById(config_id);
        if (ansConfig.isEmpty()) {
            throw new NotFoundException("No se encontro la configuracion al crear la tarea");
        }
        Configuration config = ansConfig.get();

        Optional<Project> ansProtect = projectRepository.findById(project_id);
        if (ansProtect.isEmpty()) {
            throw new NotFoundException("No se encontro el proyecto al crear la tarea");
        }
        Project project = ansProtect.get();

        Task task = new Task();

        task.setName(name);
        task.setDate(date);
        task.setProject(project);
        task.setPriority(priority);
        task.setInvestedTime(0);
        task.setDone(Boolean.FALSE);
        task.setPomQuantity(pomQuantity);
	    task.setPomFinalized(0);
        task.setPomDuration(config.getPomDuration());
        taskRepository.save(task);

//		return task;
    }

    public void update(@Validated String id, @Validated String name, @Validated Date date, @Validated String projectId, @Validated Priority priority, @Validated Integer pomQuantity) throws NotFoundException {

        validate(name);

        Optional<Task> response = taskRepository.findById(id);

        Project project = projectRepository.findById(projectId).get();

        if (response.isPresent()) {
            Task task = taskRepository.findById(id).get();
            task.setName(name);
            task.setDate(date);
            task.setProject(project);
            task.setPriority(priority);
            task.setPomQuantity(pomQuantity);

            taskRepository.save(task);
        } else {
            throw new NotFoundException("No se encontro la tarea solicitada");
        }
    }

    public void delete(@Validated String taskId) throws NotFoundException {
        taskRepository.deleteById(taskId);
    }

    public void validate(@Validated String name) throws NotFoundException {

        if (name == null || name.isEmpty()) {
            throw new NotFoundException("El nombre del Proyecto no puede ser nulo");
        }
    }

    public List<Task> findTasksOfEachProject(List<Project> projects, Date date) {
        List<Task> allTasksList = new ArrayList<>();
        projects.forEach((project) -> {
            List<Task> tasks = taskRepository.findByIdAndDate(project.getId(), date);
            allTasksList.addAll(tasks);
        });
        return allTasksList;
    }
	
	public Integer pomDurationTask(String taskId){
		Task task = taskRepository.getById(taskId);
        return task.getPomDuration();
	}

	public void toggleDoneTask(String id) {
		Task task = taskRepository.getById(id);
		boolean isDone = task.isDone();
        task.setDone(!isDone);
		
		taskRepository.save(task);
	}
	
}
