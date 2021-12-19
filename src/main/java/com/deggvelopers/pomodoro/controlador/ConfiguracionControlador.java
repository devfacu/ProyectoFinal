
package com.deggvelopers.pomodoro.controlador;

import com.deggvelopers.pomodoro.entidad.Configuracion;
import com.deggvelopers.pomodoro.repositorio.ConfiguracionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/configuracion")
public class ConfiguracionControlador {
	
	@Autowired
	private ConfiguracionRepositorio configRepo;
	
	@GetMapping("/")
	public String config(@RequestParam String config_id, ModelMap model) {
		
		Configuracion config = configRepo.getById(config_id);
		model.put("config", config);
		
		return "configuracion.html";
	}
	
}
