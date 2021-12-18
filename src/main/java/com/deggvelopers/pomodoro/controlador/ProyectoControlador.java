package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.ProyectoRepositorio;
import com.deggvelopers.pomodoro.repositorio.UsuarioRepositorio;
import com.deggvelopers.pomodoro.servicio.ProyectoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/proyecto")
public class ProyectoControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private ProyectoServicio proyectoServicio;

    @Autowired
    private ProyectoRepositorio proyectoRepo;

    @GetMapping("/")
    public String gestion(ModelMap model, @RequestParam String id) {
        try {
            Proyecto proyectos = new Proyecto();
            model.put("proyectos", proyectos);
            return "gestionProyecto.html";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "gestionProyecto.html";
        }

    }

    @PostMapping("/nuevo")

    public String crear(@RequestParam String nombre, @RequestParam String id, ModelMap model) {
        try {
            Usuario usuario = usuarioRepo.getById(id);
            proyectoServicio.crearProyecto(nombre, usuario);
            return "redirect:/principal";

        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            return "redirect:/principal";
        }
    }

    public String crear(@RequestParam String nombre, @RequestParam Usuario usuario) throws ErrorServicio {

        proyectoServicio.crearProyecto(nombre, usuario);
        return "vistaPrincipal.html";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String nombre) throws ErrorServicio {

        proyectoServicio.modificar(nombre, nombre);
        return "gestionProyecto.html";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String id, @RequestParam String nombre) throws ErrorServicio {

        proyectoServicio.eliminarProyecto(id, nombre);
        return "gestionProyecto.html";
    }
}
