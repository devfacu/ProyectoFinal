package com.deggvelopers.pomodoro.entidad;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author facundov
 */
@Entity
public class Configuracion implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String alarmaTrabajo;
	private String alarmaDescanso;
	private String ambiente;
	private Integer duracionPom;
	private Integer cantidadPom;
	private Integer descansoCorto;
	private Integer descansoLargo;
	private Integer intervaloDescansoLargo;

	public Configuracion() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlarmaTrabajo() {
		return alarmaTrabajo;
	}

	public void setAlarmaTrabajo(String alarmaTrabajo) {
		this.alarmaTrabajo = alarmaTrabajo;
	}

	public String getAlarmaDescanso() {
		return alarmaDescanso;
	}

	public void setAlarmaDescanso(String alarmaDescanso) {
		this.alarmaDescanso = alarmaDescanso;
	}

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public Integer getDuracionPom() {
		return duracionPom;
	}

	public void setDuracionPom(Integer duracionPom) {
		this.duracionPom = duracionPom;
	}

	public Integer getCantidadPom() {
		return cantidadPom;
	}

	public void setCantidadPom(Integer cantidadPom) {
		this.cantidadPom = cantidadPom;
	}

	public Integer getDescansoCorto() {
		return descansoCorto;
	}

	public void setDescansoCorto(Integer descansoCorto) {
		this.descansoCorto = descansoCorto;
	}

	public Integer getDescansoLargo() {
		return descansoLargo;
	}

	public void setDescansoLargo(Integer descansoLargo) {
		this.descansoLargo = descansoLargo;
	}

	public Integer getIntervaloDescansoLargo() {
		return intervaloDescansoLargo;
	}

	public void setIntervaloDescansoLargo(Integer intervaloDescansoLargo) {
		this.intervaloDescansoLargo = intervaloDescansoLargo;
	}

	

}
