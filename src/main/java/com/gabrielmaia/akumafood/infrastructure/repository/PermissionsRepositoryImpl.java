package com.gabrielmaia.akumafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielmaia.akumafood.domain.model.Permissions;
import com.gabrielmaia.akumafood.domain.repository.PermissionsRepository;

@Component
public class PermissionsRepositoryImpl implements PermissionsRepository{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Permissions> all() {
		return manager.createQuery("from Permissions", Permissions.class).getResultList();
	}

	@Override
	public Permissions search(Long id) {
		return manager.find(Permissions.class, id);
	}
	
	@Transactional
	@Override
	public Permissions save(Permissions permissions) {
		return manager.merge(permissions);
	}
	
	@Transactional
	@Override
	public void remove(Long id) {
		Permissions permissions = search(id);
		manager.remove(permissions);
	}

}