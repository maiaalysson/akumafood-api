package com.gabrielmaia.akumafood.domain.repository;

import java.util.List;

import com.gabrielmaia.akumafood.domain.model.City;

public interface CityRepository {
	
	List<City> all();
	City search(Long id);
	City save(City city);
	void remove(Long id);
}
