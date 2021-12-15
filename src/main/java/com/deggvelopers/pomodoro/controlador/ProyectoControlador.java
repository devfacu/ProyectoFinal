package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.ProyectoRepositorio;
import com.deggvelopers.pomodoro.servicio.ProyectoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/proyecto")
public class ProyectoControlador {

    @Autowired
    private ProyectoRepositorio proyectoRepositorio;

    @Autowired
    private ProyectoServicio proyectoServicio;

    @PostMapping("/nuevo")
    public String crear() {

        return "vistaPrincipal.html";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String nombre, @RequestParam Usuario usuario) throws ErrorServicio {

        proyectoServicio.crearProyecto(nombre, usuario);
        return "gestionProyecto.html";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String id, @RequestParam String nombre) throws ErrorServicio {

        proyectoServicio.eliminarProyecto(id, nombre);
        return "gestionProyecto.html";
    }
}
