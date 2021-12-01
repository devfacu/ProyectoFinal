package com.deggvelopers.pomodoro.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Administrador
 */
@Entity
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String nombre;
	private String apellido;
	@Column(unique = true)
	private String mail;
	private String password;
	private Boolean habilitado;
	@OneToOne
	private Configuracion configuracion;

	///constructor vacio
	public Usuario() {
	}

	///constructor lleno
	public Usuario(String id, String nombre, String apellido, String password, Boolean habilitado) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.password = password;
		this.habilitado = habilitado;
	}

	/// getters and setters
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

}
