package com.gabrielmaia.akumafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gabrielmaia.akumafood.domain.exception.EntityExceptionInUse;
import com.gabrielmaia.akumafood.domain.exception.EntityNotFound;
import com.gabrielmaia.akumafood.domain.model.City;
import com.gabrielmaia.akumafood.domain.model.State;
import com.gabrielmaia.akumafood.domain.repository.CityRepository;
import com.gabrielmaia.akumafood.domain.repository.StateRepository;

@Service
public class CityServiceRegistration {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	public City save(City city) {
		if (city.getState() != null) {
			Long stateId = city.getState().getId();
			State state = stateRepository.search(stateId);

			if (state == null)
				throw new EntityNotFound(String.format("This state code: %d, does not exist.", stateId));

			city.setState(state);
		}

		return cityRepository.save(city);
	}

	public void remove(Long id) {
		try {
			cityRepository.remove(id);

		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFound(String.format("This city code: %d, does not exist.", id));

		} catch (EntityExceptionInUse e) {
			throw new EntityExceptionInUse(
					String.format("The city of the code: %d, " + "of cannot be removed because it is in use", id));
		}
	}
}