package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.dto.user.UpdateUserRequest;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.exception.NotFoundException;
import com.deggvelopers.pomodoro.repository.UserRepository;
import com.deggvelopers.pomodoro.service.UserService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String update(ModelMap model, HttpSession session, @Valid UpdateUserRequest updateUserRequest) {
        try {
            userService.update(updateUserRequest);
            User user = userRepository.getById(updateUserRequest.getId());
            session.setAttribute("userSession", user);
            return "redirect:/principal";
        } catch (NotFoundException ex) {
            model.put("error", ex.getMessage());
            return "redirect:/principal";
        }
    }

}
