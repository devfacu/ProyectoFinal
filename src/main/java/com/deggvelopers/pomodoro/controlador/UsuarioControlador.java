package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
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

        return"gracias.html";
    }

    @GetMapping("/")
    public String login(@RequestParam(required = false) String error, ModelMap model) {

        if (error != null) {
            model.put("error", "Nombre de usuario o contraseña incorrecto .");
            return "login.html";
        } else {
            return "principal.html";
        }
    }

}
