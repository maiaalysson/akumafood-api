package com.gabrielmaia.akumafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielmaia.akumafood.domain.model.Kitchen;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {
	
	List<Kitchen> findAllByNameContaining(String name);
	
	Optional<Kitchen> findByNome(String name);
	
	boolean existsByNome(String name);	
}