package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TareaRepositorio extends JpaRepository<Tarea, String> {

    @Query("SELECT c FROM Usuario c WHERE c.fecha = :fecha AND c.completado = 0")
    public Tarea buscarPorFecha(@Param("fecha") String fecha);

    @Query("SELECT c FROM Usuario c WHERE c.completado = :completado")
    public Tarea buscarPorCompletado(@Param("completado") Boolean completado);
}
