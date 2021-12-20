
package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.entidad.Configuracion;
import com.deggvelopers.pomodoro.entidad.Usuario;
import com.deggvelopers.pomodoro.repositorio.ConfiguracionRepositorio;
import com.deggvelopers.pomodoro.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionControlador {
	
	@Autowired
	private ConfiguracionRepositorio configRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@GetMapping("/")
	public String config(@RequestParam String usuario_id, ModelMap model) {
		
		Usuario usuario = usuarioRepo.getById(usuario_id);
		Configuracion config = configRepo.getById(usuario.getConfiguracion().getId());
		model.put("config", config);
		
		return "configuracion.html";
	}
	
	@PostMapping("/guardar")
	public String guardar(ModelMap model, 
			@RequestParam String id, 
			@RequestParam String alarmaTrabajo,
			@RequestParam String alarmaDescanso,
			@RequestParam String ambiente,
			@RequestParam Integer duracionPom) {
		
		return "redirect:/configuracion/";
	}
	
}
