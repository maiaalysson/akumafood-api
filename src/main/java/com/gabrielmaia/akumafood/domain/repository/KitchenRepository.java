package com.gabrielmaia.akumafood.domain.repository;

import java.util.List;

import com.gabrielmaia.akumafood.domain.model.Kitchen;

public interface KitchenRepository {
	
	List<Kitchen> all();
	Kitchen search(Long id);
	Kitchen save(Kitchen kitchen);
	void remove(Long id);

}