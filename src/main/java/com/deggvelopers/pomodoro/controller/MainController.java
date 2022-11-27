package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.entity.Project;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repository.ProjectRepository;
import com.deggvelopers.pomodoro.service.UserService;
import java.util.List;
import javax.servlet.http.HttpSession;
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
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/")
	public String index(Model model) {
		return "index.html";
	}

	@GetMapping("/trial")
	public String trial(Model model) {
		return "trialClock.html";
	}

	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/main")
	public String main(ModelMap model, HttpSession session) {

		User user = (User) session.getAttribute("usuarioSession");
		List<Project> projects = projectRepository.findByUserId(user.getId());

		model.put("projects", projects);
		return "mainView.html";
	}

	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, ModelMap model) {

		if (error != null) {
			model.put("error", "El mail o la contraseña son incorrectos");
		}

		return "login.html";
	}

	@GetMapping("/registration")
	public String registration() {
		return "register.html";
	}

	@PostMapping("/register")
	public String register(ModelMap model, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String email, @RequestParam String contrasena1, @RequestParam String contrasena2) throws ErrorServicio {

		try {
			userService.registrar(nombre, apellido, email, contrasena1, contrasena2);
		} catch (ErrorServicio e) {
			model.put("error", e.getMessage());
			model.put("nombre", nombre);
			model.put("apellido", apellido);
			model.put("email", email);
			model.put("clave1", contrasena1);
			model.put("clave2", contrasena2);
			return "register.html";
		}

		model.put("titulo", "Bienvenido a Pomodoro App");
		model.put("descripcion", "Tu usuario fue registrado de manera satisfactoria");
		return "thanks.html";
	}
}
