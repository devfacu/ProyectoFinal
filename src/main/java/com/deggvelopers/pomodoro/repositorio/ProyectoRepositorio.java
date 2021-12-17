package com.deggvelopers.pomodoro.repositorio;

import com.deggvelopers.pomodoro.entidad.Proyecto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProyectoRepositorio extends JpaRepository<Proyecto, String> {
<<<<<<< HEAD
	
=======

    @Query("SELECT c FROM Proyecto c WHERE c.usuario_id = :usuario_id")
    public List<Proyecto> findByUserId(@Param("usuario_id") String usuario_id);

>>>>>>> b8c72ebd41d5b8a1fecd96f28828b183a78190e1
}
