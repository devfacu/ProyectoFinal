package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.exception.NotFoundException;
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
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/update")
    public String update(ModelMap model, HttpSession session, @RequestParam String id, @RequestParam String name,
                         @RequestParam String lastName, @RequestParam String email, @RequestParam String password,
                         @RequestParam String password2) {
        try {
            userService.update(id, name, lastName, email, password, password2);
            User user = userRepository.getById(id);
            session.setAttribute("userSession", user);
            return "redirect:/principal";
        } catch (NotFoundException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/principal";
        }
    }

}
