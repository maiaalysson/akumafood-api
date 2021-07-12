package com.gabrielmaia.akumafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielmaia.akumafood.domain.model.Kitchen;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {}