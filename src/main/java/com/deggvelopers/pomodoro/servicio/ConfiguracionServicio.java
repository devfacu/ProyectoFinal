
package com.deggvelopers.pomodoro.servicio;

import com.deggvelopers.pomodoro.entidad.Configuracion;
import com.deggvelopers.pomodoro.repositorio.ConfiguracionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author facundov
 */
@Service
public class ConfiguracionServicio {

	@Autowired
	private ConfiguracionRepositorio configRepo;
	
	public Configuracion crear() {
		//Se creara un objeto Configuracion con los valores predeterminados
		Configuracion config = new Configuracion();
		
		config.setAlarmaTrabajo("alarmaTrabajo");
		config.setAlarmaDescanso("alarmaDescanso");
		config.setAmbiente("ambiente");
		config.setDuracionPom(25);
		config.setDescansoCorto(5);
		config.setDescansoLargo(20);
		config.setIntervaloDescansoLargo(4);
		
		configRepo.save(config);
		
		return config;
	}
	
}
