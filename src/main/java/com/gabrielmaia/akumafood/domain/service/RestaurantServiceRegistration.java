package com.gabrielmaia.akumafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.model.Restaurant;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;
import com.gabrielmaia.akumafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantServiceRegistration {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenRepository kitchenRepository;

	public Restaurant save(Restaurant restaurant) {
		if (restaurant.getKitchen() != null) {
			Long kitchenId = restaurant.getKitchen().getId();
			Kitchen kitchen = kitchenRepository.findById(kitchenId)
					.orElseThrow(() -> new EntityNotFound(String.format("Kitchen:%d not found!", kitchenId)));

			restaurant.setKitchen(kitchen);
		}

		return restaurantRepository.save(restaurant);
	}

	public void remove(Long id) {
		try {
			restaurantRepository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFound(String.format("There is no code Restaurant with this ID: %d", id));
		}
	}
}