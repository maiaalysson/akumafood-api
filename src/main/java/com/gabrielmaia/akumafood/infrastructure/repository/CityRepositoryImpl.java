package com.gabrielmaia.akumafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielmaia.akumafood.domain.model.City;
import com.gabrielmaia.akumafood.domain.repository.CityRepository;

@Component
public class CityRepositoryImpl implements CityRepository{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<City> all() {
		return manager.createQuery("from City", City.class).getResultList();
	}

	@Override
	public City search(Long id) {
		return manager.find(City.class, id);
	}

	@Transactional
	@Override
	public City save(City city) {
		return manager.merge(city);
	}
	
	@Transactional
	@Override
	public void remove(Long id) {
		City city = search(id);
		
		if (city == null) 
			throw new EmptyResultDataAccessException(1);
		
		manager.remove(city);
	}

}