package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
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
@RequestMapping("/proyecto")
public class ProjectController {

    @Autowired
    private UserRepository usuarioRepo;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/")
    public String gestion(ModelMap model, @RequestParam String id) {
        try {
            Project proyectos = new Project();
            model.put("proyectos", proyectos);
            return "gestionProyecto.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "gestionProyecto.html";
        }

    }

    @PostMapping("/nuevo")
    public String crear(@RequestParam String nombre, @RequestParam String usuario_id, ModelMap model) {
        try {
            User user = usuarioRepo.getById(usuario_id);
            projectService.crearProyecto(nombre, user);
            return "redirect:/principal";

        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            return "redirect:/principal";
        }
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String nombre) throws ErrorServicio {

        projectService.modificar(nombre, nombre);
        return "gestionProyecto.html";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String id, @RequestParam String nombre) throws ErrorServicio {

        projectService.eliminarProyecto(id, nombre);
        return "gestionProyecto.html";
    }
}
