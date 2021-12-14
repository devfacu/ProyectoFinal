package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

public interface TareaRepositorio extends JpaRepository<Tarea, String> {

    @Query("SELECT c FROM Usuario c WHERE c.fecha = :fecha")
    public Tarea buscarPorFecha(@Param("fecha") String fecha);

    @Query("SELECT c FROM Usuario c WHERE c.completado = :completado")
    public Tarea buscarPorIncompleto(@RequestParam( required = false) Boolean completado); 

    @Query("SELECT c FROM Usuario c WHERE c.completado = :completado")
    public Tarea buscarPorCompletado(@Param("completado") Boolean completado);
}
