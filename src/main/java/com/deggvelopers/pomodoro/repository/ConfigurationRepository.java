
package com.deggvelopers.pomodoro.repository;

import com.deggvelopers.pomodoro.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, String>{
	
}
