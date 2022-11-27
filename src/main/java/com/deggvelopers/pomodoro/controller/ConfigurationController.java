
package com.deggvelopers.pomodoro.controller;

import com.deggvelopers.pomodoro.entity.Configuration;
import com.deggvelopers.pomodoro.entity.User;
import com.deggvelopers.pomodoro.repository.ConfigurationRepository;
import com.deggvelopers.pomodoro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
	
	@Autowired
	private ConfigurationRepository configurationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String config(@RequestParam String user_id, ModelMap model) {
		
		User user = userRepository.getById(user_id);
		Configuration config = configurationRepository.getById(user.getConfiguration().getId());
		model.put("config", config);
		
		return "configuration.html";
	}
	
	@PostMapping("/save")
	public String save(ModelMap model,
			@RequestParam String id, 
			@RequestParam String workAlarm,
			@RequestParam String restAlarm,
			@RequestParam String ambiance,
			@RequestParam Integer pomDuration) {
		
		return "redirect:/configuration/";
	}
	
}
