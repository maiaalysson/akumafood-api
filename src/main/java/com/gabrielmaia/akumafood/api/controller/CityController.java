package com.gabrielmaia.akumafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.City;
import com.gabrielmaia.akumafood.domain.repository.CityRepository;
import com.gabrielmaia.akumafood.domain.service.CityServiceRegistration;

@RestController
@RequestMapping("/cities")
public class CityController {
	
	@Autowired
	private CityRepository repository;
	
	@Autowired
	private CityServiceRegistration cityService;
	
	@GetMapping
	public List<City> all(){
		return repository.all();
	}
	
	@GetMapping("/{citiesId}")
	public ResponseEntity<City> search(@PathVariable Long citiesId) {
		City city = repository.search(citiesId);
		
		if (city != null)
			return ResponseEntity.ok(city);
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public City add(@RequestBody City city){
		return cityService.save(city);
	}
	
	@PutMapping("/{citiesId}")
	public ResponseEntity<City> update(@PathVariable Long citiesId, @RequestBody City city){
		City newCity = repository.search(citiesId);
		
		if (newCity != null) {
			BeanUtils.copyProperties(city, newCity, "id");
			newCity = cityService.save(newCity);
			return ResponseEntity.ok(newCity);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{citiesId}")
	public ResponseEntity<?> remove(@PathVariable Long citiesId) {
		try {
			cityService.remove(citiesId);
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntityExceptionInUse e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}