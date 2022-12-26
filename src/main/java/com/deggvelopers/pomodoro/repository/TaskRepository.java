package com.deggvelopers.pomodoro.repository;

import com.deggvelopers.pomodoro.entity.Task;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, String> {

	@Query("SELECT t FROM Task t WHERE t.project.id = :project_id AND t.date = :date AND t.done = 0")
	List<Task> findByIdAndDate(@Param("project_id") String project_id, @Param("date") Date date);

	@Query("SELECT c FROM Task c WHERE c.done = :done")
	List<Task> findByDone(@Param("done") Boolean done);

	@Query("SELECT c FROM Task c WHERE c.date > :date")
	List<Task> findByDate(@Param("date") Date date);

	@Query("SELECT c FROM Task c WHERE c.project.id = :project_id")
	List<Task> findByProject(@Param("project_id") String projectId);

}
