package com.deggvelopers.pomodoro.repository;

import com.deggvelopers.pomodoro.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

	@Query("SELECT p FROM Project p WHERE p.user.id = :user_id")
	List<Project> findByUserId(@Param("user_id") String userId);

}
