package com.gabrielmaia.akumafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielmaia.akumafood.domain.model.Restaurant;
import com.gabrielmaia.akumafood.domain.repository.RestaurantRepository;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurant> all() {
		return manager.createQuery("from Restaurant", Restaurant.class).getResultList();
	}

	@Override
	public Restaurant search(Long id) {
		return manager.find(Restaurant.class, id);
	}
	
	@Transactional
	@Override
	public Restaurant save(Restaurant restaurant) {
		return manager.merge(restaurant);
	}
	
	@Transactional
	@Override
	public void remove(Long id) {
		Restaurant restaurant = search(id);
		manager.remove(restaurant);
	}

}