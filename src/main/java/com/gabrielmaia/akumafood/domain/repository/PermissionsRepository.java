package com.gabrielmaia.akumafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielmaia.akumafood.domain.model.Permissions;

@Repository
public interface PermissionsRepository extends JpaRepository<Permissions, Long> {}