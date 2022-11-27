package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repository.UserRepository;
import com.deggvelopers.pomodoro.service.UserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository usuarioRepo;

    @PostMapping("/actualizar-perfil")
    public String modificar(ModelMap model,
            HttpSession session,
            @RequestParam String id,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String email,
            @RequestParam String password, @RequestParam String password2) {

        try {
            userService.modificar(id, nombre, apellido, email, password, password2);
            User user = usuarioRepo.getById(id);
            session.setAttribute("usuarioSession", user);
            return "redirect:/principal";
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            return "redirect:/principal";
        }
    }

}
