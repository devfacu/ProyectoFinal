package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.servicio.ProyectoServicio;
import com.deggvelopers.pomodoro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ControladorPrincipal {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ProyectoServicio proyectoServicio;

    @GetMapping("/")
    public String index(Model model) {
        return "index.html";
    }

    @GetMapping("/prueba")
    public String prueba(Model model) {
        return "reloj.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/principal")
    public String principal() {
        return "vistaPrincipal.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
       
        if (error != null) {
            model.put("error", "El mail o la contraseña son incorrectos");
        }

        return "login.html";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String contrasena1, @RequestParam String contrasena2) throws ErrorServicio {

        try {
            usuarioServicio.registrar(nombre, apellido, email, contrasena1);
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("clave1", contrasena1);
            modelo.put("clave2", contrasena2);
            return "registro.html";
        }

        modelo.put("titulo", "Bienvenido a Pomodoro App");
        modelo.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
        return "gracias.html";
    }
    
    @GetMapping("/tareas")
    public String tareas (){
        return "tareas.html"; 
    }
    
    
}
