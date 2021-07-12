package com.gabrielmaia.akumafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.functions.MergeObjects;
import com.gabrielmaia.akumafood.domain.model.City;
import com.gabrielmaia.akumafood.domain.repository.CityRepository;
import com.gabrielmaia.akumafood.domain.service.CityServiceRegistration;

@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityServiceRegistration cityService;

	@Autowired
	private MergeObjects merge;

	@GetMapping
	public List<City> all() {
		return cityRepository.findAll();
	}

	@GetMapping("/{citiesId}")
	public ResponseEntity<City> search(@PathVariable Long citiesId) {
		Optional<City> city = cityRepository.findById(citiesId);
		return city.isPresent() ? ResponseEntity.ok(city.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody City city) {
		try {
			city = cityService.save(city);
			return ResponseEntity.status(HttpStatus.CREATED).body(city);
		} catch (EntityNotFound e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{citiesId}")
	public ResponseEntity<City> update(@PathVariable Long citiesId, @RequestBody City city) {
		Optional<City> currentCity = cityRepository.findById(citiesId);

		if (currentCity.isPresent()) {
			BeanUtils.copyProperties(city, currentCity.get(), "id");
			City updateCity = cityService.save(currentCity.get());
			return ResponseEntity.ok(updateCity);
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{citiesId}")
	public ResponseEntity<City> partialUpdate(@PathVariable Long citiesId, @RequestBody City city) {
		Optional<City> currentCity = cityRepository.findById(citiesId);

		if (currentCity.isPresent()) {
			merge.objects(city, currentCity.get());
			City mergeCity = cityService.save(currentCity.get());
			return ResponseEntity.ok(mergeCity);
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