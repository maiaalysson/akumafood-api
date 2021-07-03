package com.gabrielmaia.akumafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.Kitchen;
import com.gabrielmaia.akumafood.domain.repository.KitchenRepository;

@Service
public class KitchenServiceRegistration {

	@Autowired
	private KitchenRepository kitchenRepository;

	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}
	
	public void remove(Long id) {
		try {
			kitchenRepository.remove(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFound(String.format("There is no code kitchen with this ID: %d", id));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntityExceptionInUse(String.format("Code kitchen %d cannot be removed as "
					+ "it is linked to a restaurant.", id));
		}
	}
}
