package com.gabrielmaia.akumafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gabrielmaia.akumafood.domain.model.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	// @Query("from Restaurant where name like %:received% and kitchen.id = :id")
	List<Restaurant> searchForName(@Param("received") String name, @Param("id") Long kitchen);

	//List<Restaurante> findByNameContainingAndKitchenId(String name, Long kitchen);

	List<Restaurant> queryByShippingFeeBetween(BigDecimal initialFee, BigDecimal finalFee);
	
	Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

	List<Restaurant> findTop2ByNameContaining(String name);

	int countByKitchenId(Long kitchen);
}	