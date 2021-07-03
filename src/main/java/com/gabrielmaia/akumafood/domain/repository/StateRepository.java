package com.gabrielmaia.akumafood.domain.repository;

import java.util.List;

import com.gabrielmaia.akumafood.domain.model.State;

public interface StateRepository {
	
	List<State> all();
	State search(Long id);
	State save(State state);
	void remove(Long id);
	
}
