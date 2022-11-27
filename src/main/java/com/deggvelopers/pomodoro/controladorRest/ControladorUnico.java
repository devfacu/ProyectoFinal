
package com.deggvelopers.pomodoro.controladorRest;

import com.deggvelopers.pomodoro.service.TaskService;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author facundov
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ControladorUnico {
	
	@Autowired
    TaskService taskService;

	//Recuperar informacion de los minutos del pomodoro de la tarea
	@GetMapping("/tarea/{id}/minutos")
	public Map<String, Integer> duracionPom(@PathVariable String id) {
		Integer minutos = taskService.duracionPomTarea(id);
		
		return Collections.singletonMap("minutos", minutos);
	}

	//Intercambiar el estado completado de las tareas
	@PostMapping("/tarea/{id}")
	public Map<String, String> cambioEstadoCompletado(@PathVariable String id) {
		taskService.switchCompletado(id);
		return Collections.singletonMap("ok", "ok");
	}
	
	
}
