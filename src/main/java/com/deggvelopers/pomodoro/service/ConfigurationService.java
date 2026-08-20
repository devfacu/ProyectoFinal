
package com.deggvelopers.pomodoro.service;

import com.deggvelopers.pomodoro.entity.Configuration;
import com.deggvelopers.pomodoro.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author facundov
 */
@Service
public class ConfigurationService {

	@Autowired
	private ConfigurationRepository configRepo;
	
	public Configuration create() {
		//A Configuration object will be created with default values
		Configuration config = new Configuration();
		
		config.setWorkAlarm("workAlarm");
		config.setRestAlarm("restAlarm");
		config.setAmbiance("ambiance");
		config.setPomDuration(25);
		config.setShortBreak(5);
		config.setLongBreak(20);
		config.setLongBreakInterval(4);
		
		config = configRepo.save(config);
		
		return config;
	}
	
}
