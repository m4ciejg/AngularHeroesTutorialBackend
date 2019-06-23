package com.maciejg.Herostutorial.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.maciejg.Herostutorial.model.Heroes;
import com.maciejg.Herostutorial.repo.heroRepo;
import com.maciejg.School03.exxception.ResourceNotFoundException;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class HeroesController {

	private static final Logger LOG = LoggerFactory.getLogger(HeroesController.class);
	@Autowired
	heroRepo heroRepo;
	
	@GetMapping("/heroes")
	public List<Heroes> getAll(){
		//logger.info("Getting all heroes");
		if(heroRepo.findAll().isEmpty()) {
			return (List<Heroes>) new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return heroRepo.findAll();
	}
	
	@GetMapping("/heroes/{id}")
	public Heroes getNoteById(@PathVariable(value = "id") Long id) {
	    return heroRepo.findById(id)
	    		.orElseThrow(() -> new ResourceNotFoundException("EntityNote", "id", id));
	}
	
	/**
     * Find hero with name containing string (not case sensitive)
     * 
     * @param name
     *            The string to search for.
     * @return Iterable with heroes with matching names.
     */
	
	@GetMapping("/search/name")
	public Collection<Heroes> findByName(@RequestParam("contains") String name){
		LOG.info("find name: ", name);
		return heroRepo.findByName(name);
	}
	
	@GetMapping("/search/id")
	public Collection<Heroes> findById(){
		return heroRepo.selectByid();
	}
	
	
	@PostMapping("/heroes")
	public Heroes createm(@Valid @RequestBody Heroes entity) {
		LOG.debug("Created new hero with id: " + entity.getName());
		return heroRepo.save(entity);
	}
	
	@PutMapping("/heroes/{id}")
	public void update(@PathVariable(value = "id") long id,
							@Valid @RequestBody Heroes entity ) {
		
		Heroes currentHero = heroRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("HeroEntity", "id", id));
		
		if(currentHero == null) {
			throw new ResourceNotFoundException("Hero is not found", "id= ", id);
		}
		currentHero.setName(entity.getName());
		this.heroRepo.save(currentHero);
	}

	@DeleteMapping("/heroes/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") long id){
		
		Heroes entity = heroRepo.findById(id)
								.orElseThrow(() -> new ResourceNotFoundException("EntityNote", "id", id));	
		heroRepo.delete(entity);
		
		return ResponseEntity.ok().build();
	}
}
