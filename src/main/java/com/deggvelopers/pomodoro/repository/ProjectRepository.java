package com.deggvelopers.pomodoro.repository;

import com.deggvelopers.pomodoro.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

	@Query("SELECT c FROM Proyecto c WHERE c.usuario.id = :usuario_id")
	public List<Project> findByUserId(@Param("usuario_id") String usuario_id);

}
