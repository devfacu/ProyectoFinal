package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/registro")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(String nombre, String apellido, String mail, String password, @RequestParam(required = false) String error, ModelMap model) throws Exception {

        try {
            usuarioServicio.registrarUsuario(nombre, apellido, mail, password);
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }

        return "gracias.html";
    }

    @PreAuthorize("hasanyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio() {
        return "principal.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {

        if (error != null) {
            model.put("error", "Nombre de usuario o clave incorrectos");
        }

        if (error != null) {
            model.put("error", "Ha salido correctamente de la plataforma");
        }

        return "principal.html";
    }
}
