
package com.deggvelopers.pomodoro.restController;

import com.deggvelopers.pomodoro.service.TaskService;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
//@CrossOrigin(origins = "*")
@RequestMapping("/internal")
public class UnifiedController {
	
	@Autowired
    TaskService taskService;

	@GetMapping("/task/{id}/minutes")
	public Map<String, Integer> pomDuration(@PathVariable String id) {
		Integer minutes = taskService.duracionPomTarea(id);
		
		return Collections.singletonMap("minutes", minutes);
	}

	@PostMapping("/task/{id}")
	public Map<String, String> toggleTaskState(@PathVariable String id) {
		taskService.switchCompletado(id);
		return Collections.singletonMap("ok", "ok");
	}
	
	
}
