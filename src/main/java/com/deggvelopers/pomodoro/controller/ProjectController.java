package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.repository.UserRepository;
import com.deggvelopers.pomodoro.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String manage(ModelMap model, @RequestParam String id) {
        try {
            Project project = new Project();
            model.put("projects", project);
            return "projectManagement.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "projectManagement.html";
        }

    }

    @PostMapping("/new")
    public String create(@RequestParam String name, @RequestParam String user_id, ModelMap model) {
        try {
            User user = userRepository.getById(user_id);
           projectService.create(name, user);
            return "redirect:/mainView";

        } catch (NotFoundException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/mainView";
        }
    }

    @PostMapping("/update")
    public String update(@RequestParam String name) throws NotFoundException {

        projectService.update(name, name);
        return "projectManagement.html";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String id, @RequestParam String name) throws NotFoundException {

        projectService.delete(id, name);
        return "projectManagement.html";
    }
}
