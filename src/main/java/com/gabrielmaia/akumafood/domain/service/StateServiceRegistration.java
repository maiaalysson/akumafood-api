package com.gabrielmaia.akumafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.State;
import com.gabrielmaia.akumafood.domain.repository.StateRepository;

@Service
public class StateServiceRegistration {
	
	@Autowired
	private StateRepository stateRepository;
	
	public State save(State state) {
		return stateRepository.save(state);
	}
	
	public void remove(Long id) {
		try {
			stateRepository.remove(id);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFound(String.format("This state code: %d, does not exist.", id));
		
		} catch (EntityExceptionInUse e) {
			throw new EntityExceptionInUse(String.format("The state of the code: %d, "
					+ "of cannot be removed because it is in use", id));
		}
	}
}
