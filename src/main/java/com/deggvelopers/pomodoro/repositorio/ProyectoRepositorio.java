package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, String> {

}
