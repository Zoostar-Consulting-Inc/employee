package com.zoostarinc.employee.dao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

	Optional<EmployeeEntity> findByUsername(String username);
	
}
