package com.gabrielmaia.akumafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;

@Component
public class KitchenRepositoryImpl implements KitchenRepository {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Kitchen> all() {
		return manager.createQuery("from Kitchen", Kitchen.class).getResultList();
	}

	@Override
	public Kitchen search(Long id) {
		return manager.find(Kitchen.class, id);
	}
	
	@Transactional
	@Override
	public Kitchen save(Kitchen kitchen) {
		return manager.merge(kitchen);
	}
	
	@Transactional
	@Override
	public void remove(Long id) {
		Kitchen kitchen = search(id);
		
		if (kitchen == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(kitchen);
	}

}