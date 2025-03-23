package com.zoostarinc.employee.dao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, UUID> {

	Optional<EmployeeEntity> findByUsername(String username);
	
}
