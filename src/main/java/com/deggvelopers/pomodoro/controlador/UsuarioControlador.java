//package com.deggvelopers.pomodoro.controlador;
//
//import com.deggvelopers.pomodoro.servicio.UsuarioServicio;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
//@Controller
//@RequestMapping("/usuario")
//public class UsuarioControlador {
//
//    @Autowired
//    private UsuarioServicio usuarioServicio;
//
//    @PreAuthorize("hasanyRole('ROLE_USUARIO_REGISTRADO')")
//    @GetMapping("/inicio")
//    public String inicio() {
//        return "principal.html";
//    }
//}
