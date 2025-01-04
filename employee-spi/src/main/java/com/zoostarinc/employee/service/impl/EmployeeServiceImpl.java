package com.zoostarinc.employee.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zoostarinc.employee.dao.entity.EmployeeEntity;
import com.zoostarinc.employee.dao.repository.EmployeeRepository;
import com.zoostarinc.employee.model.Employee;
import com.zoostarinc.employee.service.EmployeeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.zoostar.common.core.Transformer;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

	public static final String REQUIRED_FIELD_MISSING_ERROR_MSG = "Required field [username] is missing in request!";

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public EmployeeEntity create(Transformer<Employee> transformer) {
		var employee = transformer.transform();
		
		EmployeeEntity entity = null;
		try {
			entity = retrieveByUsername(employee.getUsername());
			if(entity != null) {
				throw new DuplicateKeyException(String.format("Employee exists with username: [%s]", employee.getUsername()));
			}
		} catch (IllegalArgumentException e) {
			log.info("Creating new employee: {}", employee);
			entity = new EmployeeEntity();
			entity.setEmail(employee.getEmail());
			entity.setFirstName(employee.getFirstName());
			entity.setLastName(employee.getLastName());
			entity.setUsername(employee.getUsername());
			entity = employeeRepository.save(entity);
		}

		return entity;
	}

	@Override
	public EmployeeEntity retrieveByUsername(String username) {
		if (!StringUtils.hasText(username)) {
			throw new IllegalArgumentException(REQUIRED_FIELD_MISSING_ERROR_MSG);
		}

		return employeeRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException(String.format("No employee found by username: [%s]", username)));
	}

	@Override
	@Transactional
	public EmployeeEntity update(Transformer<Employee> transformer) {
		var employee = transformer.transform();
		var entity = retrieveByUsername(employee.getUsername());
		log.info("Updating existing employee: {}", entity);
		entity.setEmail(employee.getEmail());
		entity.setFirstName(employee.getFirstName());
		entity.setLastName(employee.getLastName());
		return entity;
	}

}
