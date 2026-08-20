package com.deggvelopers.pomodoro.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author facundov
 */
@Entity
public class Configuration implements Serializable {

	/**
   * 
   */
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	@Column(name = "alarma_trabajo")
	private String workAlarm;
	@Column(name = "alarma_descanso")
	private String restAlarm;
	@Column(name = "ambiente")
	private String ambiance;
	@Column(name = "duracion_pom")
	private Integer pomDuration;
	@Column(name = "descanso_corto")
	private Integer shortBreak;
	@Column(name = "descanso_largo")
	private Integer longBreak;
	@Column(name = "intervalo_descanso_largo")
	private Integer longBreakInterval;

	public Configuration() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkAlarm() {
		return workAlarm;
	}

	public void setWorkAlarm(String workAlarm) {
		this.workAlarm = workAlarm;
	}

	public String getRestAlarm() {
		return restAlarm;
	}

	public void setRestAlarm(String restAlarm) {
		this.restAlarm = restAlarm;
	}

	public String getAmbiance() {
		return ambiance;
	}

	public void setAmbiance(String ambiance) {
		this.ambiance = ambiance;
	}

	public Integer getPomDuration() {
		return pomDuration;
	}

	public void setPomDuration(Integer pomDuration) {
		this.pomDuration = pomDuration;
	}

	public Integer getShortBreak() {
		return shortBreak;
	}

	public void setShortBreak(Integer shortBreak) {
		this.shortBreak = shortBreak;
	}

	public Integer getLongBreak() {
		return longBreak;
	}

	public void setLongBreak(Integer longBreak) {
		this.longBreak = longBreak;
	}

	public Integer getLongBreakInterval() {
		return longBreakInterval;
	}

	public void setLongBreakInterval(Integer longBreakInterval) {
		this.longBreakInterval = longBreakInterval;
	}

}
