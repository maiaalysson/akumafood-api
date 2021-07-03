package com.gabrielmaia.akumafood.domain.repository;

import java.util.List;

import com.gabrielmaia.akumafood.domain.model.Permissions;

public interface PermissionsRepository {
	
	List<Permissions> all();
	Permissions search(Long id);
	Permissions save(Permissions permissions);
	void remove(Long id);
	
}
