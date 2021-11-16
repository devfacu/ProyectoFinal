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
        return ".html";
    }

    @PostMapping("/")
    public String registrarUsuario(String nombre, String apellido, String mail, String password) throws Exception {
        usuarioServicio.registrarUsuario(nombre, apellido, mail, password);
        return "gracias.html";
    }

    @GetMapping("/")
    public String login(@RequestParam(required = false) String error, ModelMap model) {
        if (error != null) {
            model.put("error", "Nombre de usuario o contrase�a incorrecto .");
            return "login.html";
        } else {
            return "principal.html"; 
        }
        
    }

}
