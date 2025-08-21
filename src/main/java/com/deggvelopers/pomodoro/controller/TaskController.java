
package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.entity.Priority;
import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.Task;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.repository.ProjectRepository;
import com.deggvelopers.pomodoro.repository.TaskRepository;
import com.deggvelopers.pomodoro.repository.UserRepository;
import com.deggvelopers.pomodoro.service.TaskService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskService taskService;

	@GetMapping
	public String allProjectTasks(String attrPry_id, String project_id, ModelMap model) {

		String pry_id;
		if (project_id == null || project_id.isEmpty()) {
			pry_id = attrPry_id;
		} else {
			pry_id = project_id;
		}

		List<Project> projects = new ArrayList<>();
		projects.add(projectRepository.findById(pry_id).get());
		List<Task> tasks = taskRepository.findByProject(pry_id);

		model.put("projects", projects);
		model.put("projectList", projects);
		model.put("tasks", tasks);

		return "tasks.html";
	}

	@GetMapping("/today")
	public String listToday(@ModelAttribute String attrUsr_id, String user_id, ModelMap model) {

		String usr_id;
		if (user_id == null || user_id.isEmpty()) {
			usr_id = attrUsr_id;
		} else {
			usr_id = user_id;
		}
		Date hoy = new Date();
		List<Project> projects = projectRepository.findByUserId(usr_id);
		List<Task> tasks = taskService.findTasksOfEachProject(projects, hoy);

		model.put("vista", "Hoy");
		model.put("projects", projects);
		model.put("projectList", projects);
		model.put("tasks", tasks);

		return "tasks.html";
	}

	@GetMapping("/tomorrow")
	public String listTomorrow(@ModelAttribute String attrUsr_id, String user_id, ModelMap model) {

		String usr_id;
		if (user_id == null || user_id.isEmpty()) {
			usr_id = attrUsr_id;
		} else {
			usr_id = user_id;
		}

		Date tomorrow = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(tomorrow);
		c.add(Calendar.DATE, 1);
		tomorrow = c.getTime();

		List<Project> projects = projectRepository.findByUserId(usr_id);
		List<Task> tasks = taskService.findTasksOfEachProject(projects, tomorrow);

		model.put("vista", "Mañana");
		model.put("projects", projects);
		model.put("projectList", projects);
		model.put("tareas", tasks);

		return "tasks.html";
	}

	@GetMapping("/next")
	public String listNext(@ModelAttribute String attrUsr_id, String user_id, ModelMap model) {

		String usr_id;
		if (user_id == null || user_id.isEmpty()) {
			usr_id = attrUsr_id;
		} else {
			usr_id = user_id;
		}

		Date next = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(next);
		c.add(Calendar.DATE, 1);
		next = c.getTime();

		List<Project> projects = projectRepository.findByUserId(usr_id);
		List<Task> tasks = taskRepository.findByDate(next);

		model.put("vista", "Proximo");
		model.put("projects", projects);
		model.put("projectList", projects);
		model.put("tareas", tasks);

		return "tasks.html";
	}

	@GetMapping("/completed")
	public String listCompleted(ModelMap model, String user_id) {

		List<Task> tasks = taskRepository.findByIdAndDone(user_id, Boolean.TRUE);
		model.put("taskList", tasks);

		return "tasks.html";
	}

	@PostMapping("/new")
	public String createTask(@RequestParam String view,
							 @RequestParam String name,
							 @RequestParam String date,
							 @RequestParam String project_id,
							 @RequestParam Priority priority,
							 @RequestParam Integer pomQuantity,
							 @RequestParam String user_id,
							 ModelMap model,
							 RedirectAttributes attr) {

		view = checkView(view);

		try {
			String config_id = userRepository.getById(user_id).getConfiguration().getId();
			
			Date parsedDate = stringToDate(date);

			taskService.create(name, parsedDate, project_id, priority, pomQuantity, config_id);
			attr.addAttribute("user_id", user_id);
			attr.addAttribute("attrPry_id", project_id);
			return "redirect:/task" + view;

		} catch (NotFoundException ex) {
			model.put("error", ex.getMessage());
			attr.addAttribute("user_id", user_id);
			attr.addAttribute("attrPry_id", project_id);
			return "redirect:/task" + view;
		}
	}

	@PostMapping("/delete")
	public String delete(ModelMap model,
						   RedirectAttributes attr,
						   @RequestParam String task_id,
						   @RequestParam String view,
						   String user_id,
						   String project_id) {

		view = checkView(view);

		try {
			model.put("vista", view);
			attr.addAttribute("user_id", user_id);
			attr.addAttribute("attrPry_id", project_id);
			taskService.delete(task_id);
			return "redirect:/task/" + view;
		} catch (NotFoundException ex) {
			model.put("error", ex.getMessage());
			model.put("vista", view);
			attr.addAttribute("attrUsr_id", user_id);
			attr.addAttribute("attrPry_id", project_id);
			return "redirect:/task/" + view;
		}

	}

	@PostMapping("/update")
	public String update(
			@RequestParam String view,
			@RequestParam String user_id,
			@RequestParam String task_id,
			@RequestParam String name,
			@RequestParam String project_id,
			@RequestParam String date,
			@RequestParam Priority priority,
			@RequestParam Integer pomQuantity,
			ModelMap model,
			RedirectAttributes attr) throws NotFoundException
	{
		
		view = checkView(view);
		
		try {
			Date parsedDate = stringToDate(date);
			
			taskService.update(task_id, name, parsedDate, project_id, priority, pomQuantity);
			model.put("vista", view);
			attr.addAttribute("user_id", user_id);
			attr.addAttribute("attrPry_id", project_id);
			return "redirect:/task/" + view;
		} catch (NotFoundException e) {
			model.put("error", e.getMessage());
			model.put("vista", view);
			attr.addAttribute("user_id", user_id);
			attr.addAttribute("attrPry_id", project_id);
			return "redirect:/task/" + view;
		}
	
	}
	
	private Date stringToDate(String string){
		int year = Integer.parseInt(string.substring(0, 4));
		int month = Integer.parseInt(string.substring(5, 7));
		int day = Integer.parseInt(string.substring(8, 10));
		Date date = new Date(year - 1900, month - 1, day);
		return date;
	}

	
	private String checkView(String view) {
		if (null == view) {
			return "";
		}else switch (view) {
			case "Mañana":
				return "manana";
			default:
				return view.toLowerCase();
		}
	}
}
