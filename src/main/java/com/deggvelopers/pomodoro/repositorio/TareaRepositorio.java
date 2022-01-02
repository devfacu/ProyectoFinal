package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Tarea;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TareaRepositorio extends JpaRepository<Tarea, String> {

	@Query("SELECT c FROM Tarea c WHERE c.proyecto.id = :proyecto_id AND c.fecha = :fecha AND c.completado = 0")
	public List<Tarea> buscarPorFecha(@Param("proyecto_id") String proyecto_id, @Param("fecha") Date fecha);

	@Query("SELECT c FROM Tarea c WHERE c.completado = :completado")
	public List<Tarea> buscarPorCompletado(@Param("completado") Boolean completado);

	@Query("SELECT c FROM Tarea c WHERE c.fecha > :fecha")
	public List<Tarea> buscarPorProximo(@Param("fecha") Date fecha);

	@Query("SELECT c FROM Tarea c JOIN c.proyecto p WHERE p.id = :proyecto_id")
	public List<Tarea> buscarPorProyecto(@Param("proyecto_id") String proyecto_id);

}
