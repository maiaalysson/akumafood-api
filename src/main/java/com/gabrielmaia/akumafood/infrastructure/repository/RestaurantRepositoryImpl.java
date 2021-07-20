package com.gabrielmaia.akumafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.gabrielmaia.akumafood.domain.model.Restaurant;
import com.gabrielmaia.akumafood.domain.repository.RestaurantRepositoryCustomized;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustomized {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurant> find(String name, BigDecimal initialFee, BigDecimal finalFee) {
		var jpql = new StringBuilder();
		var parameterQuery = new HashMap<String, Object>(); 
		
		jpql.append("from Restaurant where 0 = 0 ");
		
		if (StringUtils.hasLength(name)) {
			jpql.append("and name like :name");
			parameterQuery.put("name", "%" + name + "%");
		}
		
		if (initialFee != null) {
			jpql.append("and shipping_fee >= :initialFee ");
			parameterQuery.put("initialFee", initialFee);
		}
		
		if (finalFee != null) {
			jpql.append("and shipping_fee <= :finalFee ");
			parameterQuery.put("initialFee", initialFee);
		}
		
		TypedQuery<Restaurant> query = manager.createQuery(jpql.toString(), Restaurant.class);
		parameterQuery.forEach((key, value) -> query.setParameter(key, value));
		
		return query.getResultList();
		
	}
}