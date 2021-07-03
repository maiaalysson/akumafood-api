package com.gabrielmaia.akumafood.domain.repository;

import java.util.List;

import com.gabrielmaia.akumafood.domain.model.Restaurant;

public interface RestaurantRepository {
	
	List<Restaurant> all();
	Restaurant search(Long id);
	Restaurant save(Restaurant restaurant);
	void remove(Long id);
	
}
