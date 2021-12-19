package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, String> {

	@Query("SELECT c FROM Proyecto c WHERE c.usuario.id = :usuario_id")
	public List<Proyecto> findByUserId(@Param("usuario_id") String usuario_id);

}
