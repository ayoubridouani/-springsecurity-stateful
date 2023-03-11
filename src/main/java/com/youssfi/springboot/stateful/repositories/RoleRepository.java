package com.youssfi.springboot.stateful.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youssfi.springboot.stateful.entities.AppRole;

@Repository
public interface RoleRepository extends JpaRepository<AppRole, Long>{
	public AppRole findByName(String rolename);
}
