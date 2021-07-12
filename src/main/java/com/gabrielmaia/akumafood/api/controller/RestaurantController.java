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

import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.Restaurant;
import com.gabrielmaia.akumafood.domain.repository.RestaurantRepository;
import com.gabrielmaia.akumafood.domain.service.RestaurantServiceRegistration;
import com.gabrielmaia.akumafood.infrastructure.repository.MergeObjects;

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantServiceRegistration restaurantService;

	@Autowired
	private MergeObjects merge;

	@GetMapping
	public List<Restaurant> all() {
		return restaurantRepository.findAll();
	}

	@GetMapping("/{restaurantsId}")
	public ResponseEntity<Restaurant> search(@PathVariable Long restaurantsId) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantsId);
		return restaurant.isPresent() ? ResponseEntity.ok(restaurant.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> add(@RequestBody Restaurant restaurant) {
		try {
			restaurant = restaurantService.save(restaurant);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
		} catch (EntityNotFound e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("/{restaurantsId}")
	public ResponseEntity<?> update(@PathVariable Long restaurantsId, @RequestBody Restaurant restaurant) {
		try {
			Optional<Restaurant> currentRestaurant = restaurantRepository.findById(restaurantsId);

			if (currentRestaurant.isPresent()) {
				BeanUtils.copyProperties(restaurant, currentRestaurant.get(), "id");
				Restaurant updateRestaurant = restaurantService.save(currentRestaurant.get());
				return ResponseEntity.ok(updateRestaurant);
			}

			return ResponseEntity.notFound().build();
		} catch (EntityNotFound e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping("/{restaurantsId}")
	public ResponseEntity<Restaurant> partialUpdate(@PathVariable Long restaurantsId, @RequestBody Restaurant restaurant) {
		Optional<Restaurant> currentRestaurant = restaurantRepository.findById(restaurantsId);

		if (currentRestaurant.isPresent()) {
			merge.objects(restaurant, currentRestaurant.get());
			Restaurant mergeRestaurant = restaurantService.save(currentRestaurant.get());
			return ResponseEntity.ok(mergeRestaurant);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{restaurantsId}")
	public ResponseEntity<Restaurant> remove(@PathVariable Long restaurantsId) {
		try {
			restaurantService.remove(restaurantsId);
			return ResponseEntity.noContent().build();
			
		} catch (EntityNotFound e) {
			return ResponseEntity.notFound().build();
		}
	}
}