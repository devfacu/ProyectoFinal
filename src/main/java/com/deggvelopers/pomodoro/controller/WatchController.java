
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
@RequestMapping("/watch")
public class WatchController {
	
	@GetMapping
	public String clockView(ModelMap model, String taskName) {
		model.addAttribute("taskName", taskName);
		return "clock.html";
	}
	
}
