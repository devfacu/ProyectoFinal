package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/modificar")
    public String inicio(String email) {
        usuarioServicio.loadUserByUsername(email);
        return "principal.html";
    }

}
