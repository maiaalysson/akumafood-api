package com.gabrielmaia.akumafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gabrielmaia.akumafood.domain.model.PaymentMethod;
import com.gabrielmaia.akumafood.domain.repository.PaymentMethodRepository;

@Component
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<PaymentMethod> all() {
		return manager.createQuery("from PaymentMethod", PaymentMethod.class).getResultList();
	}

	@Override
	public PaymentMethod search(Long id) {
		return manager.find(PaymentMethod.class, id);
	}
	
	@Transactional
	@Override
	public PaymentMethod save(PaymentMethod method) {
		return manager.merge(method);
	}
	
	@Transactional
	@Override
	public void remove(Long id) {
		PaymentMethod method = search(id);
		manager.remove(method);
	}

}