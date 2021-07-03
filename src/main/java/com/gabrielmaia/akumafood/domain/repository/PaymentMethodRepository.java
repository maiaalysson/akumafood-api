package com.gabrielmaia.akumafood.domain.repository;

import java.util.List;

import com.gabrielmaia.akumafood.domain.model.PaymentMethod;

public interface PaymentMethodRepository {
	
	List<PaymentMethod> all();
	PaymentMethod search(Long id);
	PaymentMethod save(PaymentMethod method);
	void remove(Long id);
	
}
