package com.deggvelopers.pomodoro.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Task implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat( pattern="dd-MM-yyyy")
	private Date fecha;
	@JoinColumn(referencedColumnName = "id")
	@ManyToOne
	private Project project;
	@Enumerated(EnumType.STRING)
	private Priority priority;
	private Integer tiempoInvertido;
	private Boolean completado;
	private Integer cantidadPom;
	private Integer pomRealizados;
	private Integer duracionPom;

	/// CONSTRUCTOR VACIO ///
	public Task() {
	}

	////GETTERS Y SETTERS //////////
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Priority getPrioridad() {
		return priority;
	}

	public void setPrioridad(Priority priority) {
		this.priority = priority;
	}

	public Integer getTiempoInvertido() {
		return tiempoInvertido;
	}

	public void setTiempoInvertido(Integer tiempoInvertido) {
		this.tiempoInvertido = tiempoInvertido;
	}

	public Boolean getCompletado() {
		return completado;
	}

	public void setCompletado(Boolean completado) {
		this.completado = completado;
	}

	public Integer getCantidadPom() {
		return cantidadPom;
	}

	public void setCantidadPom(Integer cantidadPom) {
		this.cantidadPom = cantidadPom;
	}

	public Integer getDuracionPom() {
		return duracionPom;
	}

	public void setDuracionPom(Integer duracionPom) {
		this.duracionPom = duracionPom;
	}

	public Integer getPomRealizados() {
		return pomRealizados;
	}

	public void setPomRealizados(Integer pomRealizados) {
		this.pomRealizados = pomRealizados;
	}
	

	/**
	 * @return the proyecto
	 */
	public Project getProyecto() {
		return project;
	}

	/**
	 * @param project the proyecto to set
	 */
	public void setProyecto(Project project) {
		this.project = project;
	}
}
