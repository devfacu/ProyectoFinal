package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrador
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

	@Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
	public Usuario buscarPorMail(@Param("mail") String mail);

}
