
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
	
	public Configuration crear() {
		//Se creara un objeto Configuracion con los valores predeterminados
		Configuration config = new Configuration();
		
		config.setAlarmaTrabajo("alarmaTrabajo");
		config.setAlarmaDescanso("alarmaDescanso");
		config.setAmbiente("ambiente");
		config.setDuracionPom(25);
		config.setDescansoCorto(5);
		config.setDescansoLargo(20);
		config.setIntervaloDescansoLargo(4);
		
		config = configRepo.save(config);
		
		return config;
	}
	
}
