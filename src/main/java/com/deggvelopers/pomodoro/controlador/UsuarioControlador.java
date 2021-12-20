package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.errores.ErrorServicio;
import com.deggvelopers.pomodoro.repositorio.UsuarioRepositorio;
import com.deggvelopers.pomodoro.servicio.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;

    @PostMapping("/actualizar-perfil")
    public String modificar(ModelMap model, 
			HttpSession session, 
			@RequestParam String id, 
			@RequestParam String nombre, 
			@RequestParam String apellido, 
			@RequestParam String email, 
			 String password) {
		
		try {
			usuarioServicio.modificar(id, nombre, apellido, email, password);
			Usuario usuario = usuarioRepo.getById(id);
			session.setAttribute("usuarioSession", usuario);
			return "redirect:/principal";
		} catch (ErrorServicio ex) {
			model.put("error", ex.getMessage());
			return "redirect:/principal";
		}
    }

}
