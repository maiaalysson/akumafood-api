package com.gabrielmaia.akumafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.gabrielmaia.akumafood.domain.model.Restaurant;

public interface RestaurantRepositoryCustomized {

	List<Restaurant> find(String name, BigDecimal initialFee, BigDecimal finalFee);

}