package com.deggvelopers.pomodoro.repository;

import com.deggvelopers.pomodoro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

	@Query("SELECT c FROM Usuario c WHERE c.mail = :mail")
	public User buscarPorMail(@Param("mail") String mail);

}
