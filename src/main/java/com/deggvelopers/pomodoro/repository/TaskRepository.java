package com.deggvelopers.pomodoro.repository;

import com.deggvelopers.pomodoro.entity.Task;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, String> {

	@Query("SELECT c FROM Tarea c WHERE c.proyecto.id = :proyecto_id AND c.fecha = :fecha AND c.completado = 0")
	public List<Task> buscarPorFecha(@Param("proyecto_id") String proyecto_id, @Param("fecha") Date fecha);

	@Query("SELECT c FROM Tarea c WHERE c.completado = :completado")
	public List<Task> buscarPorCompletado(@Param("completado") Boolean completado);

	@Query("SELECT c FROM Tarea c WHERE c.fecha > :fecha")
	public List<Task> buscarPorProximo(@Param("fecha") Date fecha);

	@Query("SELECT c FROM Tarea c WHERE c.proyecto.id = :proyecto_id")
	public List<Task> buscarPorProyecto(@Param("proyecto_id") String proyecto_id);

}
