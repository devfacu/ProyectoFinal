
package com.deggvelopers.pomodoro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author facundov
 */

@Controller
@RequestMapping("/reloj")
public class WatchController {
	
	@GetMapping("")
	public String devolverReloj(ModelMap model, String nombreTarea) {
		model.addAttribute("nombre_tarea", nombreTarea);
		return "clock.html";
	}
	
}
