package com.maciejg.Herostutorial.repo;

import java.util.Collection;

import org.springframework.boot.context.properties.source.IterableConfigurationPropertySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maciejg.Herostutorial.model.Heroes;

public interface heroRepo extends JpaRepository<Heroes, Long> {
	//public String sayHello(String a);
	
	@Query("from Heroes h where lower(h.name) like CONCAT('%',lower(:contains), '%')")
	public Collection<Heroes> findByName(@Param("contains") String name);
}
